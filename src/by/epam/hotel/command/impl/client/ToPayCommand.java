package by.epam.hotel.command.impl.client;

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
import by.epam.hotel.logic.ToPayLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ToPayCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(ToPayCommand.class);

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
					BigDecimal currentAmount = ToPayLogic.getCurrentAmount(sessionData.getLogin());
					if(currentAmount != null) {
						sessionData.setCurrentAmount(currentAmount);
						page = ConfigurationManager.getProperty("path.page.paypage");
					}else {
						request.setAttribute("errorFindBankAccountMessage", MessageManager.getProrerty("message.findinbankaccounterror"));
						page = ConfigurationManager.getProperty("path.page.infoforpayment");
						router.setType(RouterType.FORWARD);
					}
				} else {
					sessionData.setAvailableRoomList(updatedAvailableRoomList);
					page = ConfigurationManager.getProperty("path.page.alreadyorderedroom");
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;

	}

}
