package by.epam.hotel.command.impl.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ApproveChangeRoomLogic;
import by.epam.hotel.logic.ToAllRoomsLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class ApproveChangeRoomCommand implements ActionCommand{
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int number = sessionData.getRoomToChange().getNumber();
			String capacity = request.getParameter(ParameterConstant.CAPACITY);
			String roomClass = request.getParameter(ParameterConstant.CLASS);
			String price = request.getParameter(ParameterConstant.PRICE);
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
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_ROOMS);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_ROOM_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_ROOM_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_ROOM);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					throw new CommandException(e);
				}	
			}else {
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_ROOM);
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateInputData(String capacity, String price, HttpServletRequest request) {
		boolean result = true;
		
		if (!validateCapacity(capacity)) {
			request.setAttribute(AttributeConstant.ERROR_CAPACITY_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_CAPACITY_ERROR));
			result = false;
		}
		if (!validatePrice(price)) {
			request.setAttribute(AttributeConstant.WRONG_INPUT_AMOUNT, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_INPUT_AMOUNT_ERROR));
			result = false;
		}
		return result;
	}
	
	private boolean validateCapacity(String capacity) {
		boolean flag = false;
		Pattern pattern = Pattern.compile(ValidationConstant.CAPACITY_PATTERN);
		Matcher matcher = pattern.matcher(capacity);
		if(matcher.matches()) {
			flag = Integer.parseInt(capacity)<=65535;
		}
		return flag;
	}
	
	private boolean validatePrice(String price) {
		Pattern pattern = Pattern.compile(ValidationConstant.PRICE_PATTERN);
		Matcher matcher = pattern.matcher(price);
		return matcher.matches();
	}
	
	private BigDecimal parseToBigDecimal (String price) throws ParseException {
        String processNumber = price.replace(ValidationConstant.COMMA, ValidationConstant.DOT);
        BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
        return result;
	}
	
}
