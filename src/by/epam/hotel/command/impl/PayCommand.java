package by.epam.hotel.command.impl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.FindRoomLogic;
import by.epam.hotel.logic.PayLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class PayCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(PayCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			Room chosenRoom = sessionData.getChosenRoom();
			LocalDate from = sessionData.getFrom();
			LocalDate to = sessionData.getTo();
			try {
				List<Room> updatedAvailableRoomList = FindRoomLogic.findAvailableRoom(chosenRoom.getCapacity(),
						chosenRoom.getClassRoom(), from, to);
				if (updatedAvailableRoomList.contains(chosenRoom)) {
					BigDecimal currentAmount = sessionData.getCurrentAmount();
					BigDecimal toPay = sessionData.getToPay();
					if(currentAmount.compareTo(toPay)>=0) {
						int room_number = sessionData.getChosenRoom().getNumber();
						int client_id = sessionData.getChosenClient().getId();
						String login = sessionData.getLogin();
						if(PayLogic.doPay(room_number, client_id, login, from, to, toPay)) {
							page = ConfigurationManager.getProperty("path.page.successpayment");
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute("PaymentErrorMessage", MessageManager.getProrerty("message.paymenterror"));
							page = ConfigurationManager.getProperty("path.page.paypage");
							router.setType(RouterType.FORWARD);
						}
					}else {
						request.setAttribute("errorEnoughMoneyMessage", MessageManager.getProrerty("message.enoughmoneyerror"));
						page = ConfigurationManager.getProperty("path.page.paypage");
						router.setType(RouterType.FORWARD);
					}
				} else {
					sessionData.setAvailableRoomList(updatedAvailableRoomList);
					page = ConfigurationManager.getProperty("path.page.alreadyorderedroom");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
