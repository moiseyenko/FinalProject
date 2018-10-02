package by.epam.hotel.command.impl.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import by.epam.hotel.logic.ApproveChangeRoomLogic;
import by.epam.hotel.logic.ToAllRoomsLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ApproveChangeRoomCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ApproveChangeRoomCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int number = sessionData.getRoomToChange().getNumber();
			String capacity = request.getParameter("capacity");
			String roomClass = request.getParameter("class");
			String price = request.getParameter("price");
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			if(validateInputData(capacity, price, request)) {
				try {
					int newCapacity = Integer.parseInt(capacity);
					BigDecimal newPrice = parseToBigDecimal(price);
					Room updatedRoom = new Room(number, roomClass, newCapacity, newPrice);
					try {
						if(ApproveChangeRoomLogic.changeRoom(updatedRoom)) {
							List<Room> roomList = ToAllRoomsLogic.getRoomsList(currentPage,
									recordsPerPage);
							sessionData.setRoomList(roomList);
							page = ConfigurationManager.getProperty("path.page.allrooms");
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute("errorChangeRoomMessage", MessageManager.getProrerty("message.changeroomerror"));
							page = ConfigurationManager.getProperty("path.page.changeroom");
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						LOG.error(e);
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					LOG.error(e);
					throw new CommandException(e);
				}	
			}else {
				page = ConfigurationManager.getProperty("path.page.changeroom");
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateInputData(String capacity, String price, HttpServletRequest request) {
		boolean result = true;
		
		if (!validateCapacity(capacity)) {
			request.setAttribute("errorCapacityMessage", MessageManager.getProrerty("message.capacityerror"));
			result = false;
		}
		if (!validatePrice(price)) {
			request.setAttribute("wrongInputAmount", MessageManager.getProrerty("message.wronginputamount"));
			result = false;
		}
		return result;
	}
	
	private boolean validateCapacity(String capacity) {
		boolean flag = false;
		String LOGIN_PATTERN = "^[0-9]{1,5}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(capacity);
		if(matcher.matches()) {
			flag = Integer.parseInt(capacity)<=65535;
		}
		return flag;
	}
	
	private boolean validatePrice(String price) {
		String INPUT_PATTERN = "^[0-9]{1,10}.[0-9]{0,2}$";
		Pattern pattern = Pattern.compile(INPUT_PATTERN);
		Matcher matcher = pattern.matcher(price);
		return matcher.matches();
	}
	
	private BigDecimal parseToBigDecimal (String price) throws ParseException {
        String processNumber = price.replace(",", ".");
        BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
        return result;
	}
	
}
