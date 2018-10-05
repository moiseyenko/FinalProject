package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.FindRoomLogic;
import by.epam.hotel.logic.ToPayLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class ToPayCommand implements ActionCommand {

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
				List<Room> updatedAvailableRoomList = FindRoomLogic.findAvailableRoom(chosenRoom.getCapacity(),
						chosenRoom.getClassRoom(), from, to);
				if (updatedAvailableRoomList.contains(chosenRoom)) {
					BigDecimal currentAmount = ToPayLogic.getCurrentAmount(sessionData.getLogin());
					if(currentAmount != null) {
						sessionData.setCurrentAmount(currentAmount);
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
					}else {
						request.setAttribute(AttributeConstant.ERROR_FIND_BANK_ACCOUNT_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_FIND_IN_BANK_ACCOUNT_ERROR));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_INFO_FOR_PAYMENT);
						router.setType(RouterType.FORWARD);
					}
				} else {
					sessionData.setAvailableRoomList(updatedAvailableRoomList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALREADY_ORDERED_ROOM);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}
}
