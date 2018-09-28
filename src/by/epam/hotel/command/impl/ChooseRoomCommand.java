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
import by.epam.hotel.util.ConfigurationManager;

public class ChooseRoomCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(ChooseRoomCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			int chosenRoomNumber = Integer.parseInt(request.getParameter("number"));
			List<Room> chachedAvailableRoomList = sessionData.getAvailableRoomList();
			Room chosenRoom = findChosenRoom(chachedAvailableRoomList, chosenRoomNumber);
			LocalDate from = sessionData.getFrom();
			LocalDate to = sessionData.getTo();
			try {
				List<Room> updatedAvailableRoomList = FindRoomLogic.findAvailableRoom(chosenRoom.getCapacity(), chosenRoom.getClassRoom(), from, to);
				if (updatedAvailableRoomList.contains(chosenRoom)) {
					sessionData.setChosenRoom(chosenRoom);
					int timeStampDiff = to.getDayOfYear()-from.getDayOfYear();
					int toPay = (timeStampDiff!=0?timeStampDiff:1)*chosenRoom.getPrice().intValue();
					BigDecimal toPayBigDecimal = BigDecimal.valueOf(toPay);
					sessionData.setToPay(toPayBigDecimal);
					page = ConfigurationManager.getProperty("path.page.infoforpayment");
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

	private Room findChosenRoom (List<Room> chachedAvailableRoomList, int chosenRoomNumber) {
		Room result = null;
		for(Room chachedAvailableRoom: chachedAvailableRoomList){
			if(chachedAvailableRoom.getNumber()==chosenRoomNumber) {
				result =  chachedAvailableRoom;
			}
		}
		return result;
	}
	
	
}
