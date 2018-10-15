package by.epam.hotel.command.impl.admin;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

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
 * and is used to approve modification of specified room.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ApproveChangeRoomCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If parameters: capacity and price are invalid or if the room with specified parameters 
	 * cannot be updated, method will return user by 
	 * {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will approve modification of specified room and return admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with all rooms.
	 */
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
			if(validateInputData(capacity, price, request, sessionData)) {
				try {
					int newCapacity = Integer.parseInt(capacity);
					BigDecimal newPrice = parseToBigDecimal(price);
					Room updatedRoom = new Room(number, roomClass, newCapacity, newPrice);
					try {
						if(AdminService.approveChangeRoom(updatedRoom)) {
							List<Room> roomList = AdminService.getRoomsList(currentPage,
									recordsPerPage);
							sessionData.setRoomList(roomList);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_ROOMS);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_ROOM_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_ROOM_ERROR, sessionData.getLocale()));
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
	
	private boolean validateInputData(String capacity, String price, HttpServletRequest request, SessionData sessionData) {
		boolean result = true;
		
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
