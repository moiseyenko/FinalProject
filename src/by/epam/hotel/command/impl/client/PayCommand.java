package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.FindRoomLogic;
import by.epam.hotel.logic.OrderLogic;
import by.epam.hotel.logic.PayLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class PayCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			Room chosenRoom = sessionData.getChosenRoom();
			LocalDate from = sessionData.getFrom();
			LocalDate to = sessionData.getTo();
			try {
				if (!FindRoomLogic.checkClientInBlacklist(sessionData.getChosenClient())) {
					List<Room> updatedAvailableRoomList = FindRoomLogic.findAvailableRoom(chosenRoom.getCapacity(),
							chosenRoom.getClassRoom(), from, to);
					if (updatedAvailableRoomList.contains(chosenRoom)) {
						BigDecimal currentAmount = sessionData.getCurrentAmount();
						BigDecimal toPay = sessionData.getToPay();
						if (currentAmount.compareTo(toPay) >= 0) {
							int room_number = sessionData.getChosenRoom().getNumber();
							Client client = sessionData.getChosenClient();
							String login = sessionData.getLogin();
							if (PayLogic.doPay(room_number, client, login, from, to, toPay)) {
								page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_PAYMENT);
								router.setType(RouterType.REDIRECT);
							} else {
								request.setAttribute(AttributeConstant.ERROR_PAYMENT_MESSAGE,
										MessageManager.getProrerty(PropertyConstant.MESSAGE_PAYMENT_ERROR));
								page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
								router.setType(RouterType.FORWARD);
							}
						} else {
							request.setAttribute(AttributeConstant.ERROR_ENOUGH_MONEY_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_ENOUGH_MONEY_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
							router.setType(RouterType.FORWARD);
						}
					} else {
						sessionData.setAvailableRoomList(updatedAvailableRoomList);
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALREADY_ORDERED_ROOM);
						router.setType(RouterType.FORWARD);
					}
				} else {
					sessionData.setClients(OrderLogic.getClientList(sessionData.getLogin()));
					request.setAttribute(AttributeConstant.ERROR_BLACKLIST_CLIENT_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_BLACKLIST_CLIENT_ERROR));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
					router.setType(RouterType.FORWARD);
				}

			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
