package by.epam.hotel.command.impl.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.AdminService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;
import by.epam.hotel.util.validator.RoomValidator;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to creation of new room.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class CreateRoomCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If parameters: number, capacity and price are invalid or if new room cannot be created, 
	 * method will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will create new room and send admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with message of successfull
	 * creation.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String number = request.getParameter(ParameterConstant.NUMBER);
			String capacity = request.getParameter(ParameterConstant.CAPACITY);
			String roomClass = request.getParameter(ParameterConstant.CLASS);
			String price = request.getParameter(ParameterConstant.PRICE);
			if(validateInputData(number, capacity, price, request, sessionData)) {
				try {
					int newNumber = Integer.parseInt(number);
					int newCapacity = Integer.parseInt(capacity);
					BigDecimal newPrice = parseToBigDecimal(price);
					Room newRoom = new Room(newNumber, roomClass, newCapacity, newPrice);
					try {
						if(AdminService.createRoom(newRoom)) {
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CREATE_ROOM);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CREATE_ROOM_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CREATE_ROOM_ERROR, sessionData.getLocale()));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_ROOM);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					throw new CommandException(e);
				}	
			}else {
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_ROOM);
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	
	private boolean validateInputData(String number, String capacity, String price, HttpServletRequest request, SessionData sessionData) {
		boolean result = true;
		
		if (!RoomValidator.validateNumber(number)) {
			request.setAttribute(AttributeConstant.ERROR_NUMBER_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_NUMBER_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!RoomValidator.validateNumber(capacity)) {
			request.setAttribute(AttributeConstant.ERROR_CAPACITY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_CAPACITY_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!RoomValidator.validateCurrency(price)) {
			request.setAttribute(AttributeConstant.WRONG_INPUT_AMOUNT,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_INPUT_AMOUNT_ERROR, sessionData.getLocale()));
			result = false;
		}
		return result;
	}
	
	private BigDecimal parseToBigDecimal (String price) throws ParseException {
        String processNumber = price.replace(ValidationConstant.COMMA, ValidationConstant.DOT);
        BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
        return result;
	}
	
	

}
