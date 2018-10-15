package by.epam.hotel.command.impl.client;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;
import by.epam.hotel.util.validator.ClientValidator;
import by.epam.hotel.util.validator.RoomValidator;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to find a suitable hotel room according to the criteria specified by the
 * client.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class FindRoomCommand implements ActionCommand {

	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} method will return user to welcome page. If inputted search
	 * parameters: first name, last name, passport, nationality, capacity, from, to
	 * are incorrect or if cpecified client is in black list, method will return
	 * client back to order page. Otherwise method will send client to page with
	 * available rooms.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			String fname = request.getParameter(ParameterConstant.FISRT_NAME);
			String lname = request.getParameter(ParameterConstant.LAST_NAME);
			String passport = request.getParameter(ParameterConstant.PASSPORT);
			String nationality = request.getParameter(ParameterConstant.NATIONATILY);
			String roomClass = request.getParameter(ParameterConstant.CLASS);
			String capacity = request.getParameter(ParameterConstant.CAPACITY);
			String from = request.getParameter(ParameterConstant.FROM);
			String to = request.getParameter(ParameterConstant.TO);
			List<Nationality> nationalities = sessionData.getNationalities();
			if (validateInputData(fname, lname, passport, nationality, nationalities, capacity, from, to, request,
					sessionData)) {
				try {
					Client chosenClient = new Client(fname, lname, passport, nationality);
					if (!ClientService.checkClientInBlacklist(chosenClient)) {
						chosenClient = ClientService.getClient(chosenClient);
						sessionData.setChosenClient(chosenClient);
						LocalDate localFrom = LocalDate.parse(from);
						LocalDate localTo = LocalDate.parse(to);
						int intCapacity = Integer.parseInt(capacity);
						sessionData.setFrom(localFrom);
						sessionData.setTo(localTo);
						List<Room> availableRoomList = ClientService.findAvailableRoom(intCapacity, roomClass,
								localFrom, localTo);
						sessionData.setAvailableRoomList(availableRoomList);
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_AVAILABLE_ROOM);
					} else {
						sessionData.setClients(ClientService.getClientList(sessionData.getLogin()));
						request.setAttribute(AttributeConstant.ERROR_BLACKLIST_CLIENT_MESSAGE, MessageManager
								.getProrerty(PropertyConstant.MESSAGE_BLACKLIST_CLIENT_ERROR, sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

	private boolean validateInputData(String fname, String lname, String passport, String nationality,
			List<Nationality> nationalities, String capacity, String from, String to, HttpServletRequest request,
			SessionData sessionData) {
		boolean result = true;
		if (!ClientValidator.validateFName(fname)) {
			request.setAttribute(AttributeConstant.ERROR_FIRST_NAME_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_FIRST_NAME_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!ClientValidator.validateLName(lname)) {
			request.setAttribute(AttributeConstant.ERROR_LAST_NAME_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_LAST_NAME_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!ClientValidator.validatePassport(passport)) {
			request.setAttribute(AttributeConstant.ERROR_PASSPORT_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSPORT_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!ClientValidator.validateNationality(nationality, nationalities)) {
			request.setAttribute(AttributeConstant.ERROR_NATIONALITY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_NATIONALITY_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!RoomValidator.validateNumber(capacity)) {
			request.setAttribute(AttributeConstant.ERROR_CAPACITY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_CAPACITY_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!RoomValidator.validateFromTo(from, to)) {
			request.setAttribute(AttributeConstant.ERROR_FROM_TO_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_FROM_TO_ERROR, sessionData.getLocale()));
			result = false;
		}
		return result;
	}

}
