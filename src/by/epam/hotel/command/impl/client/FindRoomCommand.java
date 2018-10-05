package by.epam.hotel.command.impl.client;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.FindRoomLogic;
import by.epam.hotel.logic.OrderLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class FindRoomCommand implements ActionCommand {

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
			if (validateInputData(fname, lname, passport, nationality, nationalities, capacity, from, to, request)) {
				try {
					Client chosenClient = new Client(fname, lname, passport, nationality);
					if (!FindRoomLogic.checkClientInBlacklist(chosenClient)) {
						chosenClient = FindRoomLogic.getClient(chosenClient);
						sessionData.setChosenClient(chosenClient);
						LocalDate localFrom = LocalDate.parse(from);
						LocalDate localTo = LocalDate.parse(to);
						int intCapacity = Integer.parseInt(capacity);
						sessionData.setFrom(localFrom);
						sessionData.setTo(localTo);
						List<Room> availableRoomList = FindRoomLogic.findAvailableRoom(intCapacity, roomClass,
								localFrom, localTo);
						System.out.println(availableRoomList);
						sessionData.setAvailableRoomList(availableRoomList);
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_AVAILABLE_ROOM);
					} else {
						sessionData.setClients(OrderLogic.getClientList(sessionData.getLogin()));
						request.setAttribute(AttributeConstant.ERROR_BLACKLIST_CLIENT_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_BLACKLIST_CLIENT_ERROR));
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
			List<Nationality> nationalities, String capacity, String from, String to, HttpServletRequest request) {
		boolean result = true;
		if (!validateFName(fname)) {
			request.setAttribute(AttributeConstant.ERROR_FIRST_NAME_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_FIRST_NAME_ERROR));
			result = false;
		}
		if (!validateLName(lname)) {
			request.setAttribute(AttributeConstant.ERROR_LAST_NAME_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_LAST_NAME_ERROR));
			result = false;
		}
		if (!validatePassport(passport)) {
			request.setAttribute(AttributeConstant.ERROR_PASSPORT_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSPORT_ERROR));
			result = false;
		}
		if (!validateNationality(nationality, nationalities)) {
			request.setAttribute(AttributeConstant.ERROR_NATIONALITY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_NATIONALITY_ERROR));
			result = false;
		}
		if (!validateCapacity(capacity)) {
			request.setAttribute(AttributeConstant.ERROR_CAPACITY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_CAPACITY_ERROR));
			result = false;
		}
		if (!validateFromTo(from, to)) {
			request.setAttribute(AttributeConstant.ERROR_FROM_TO_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_FROM_TO_ERROR));
			result = false;
		}
		return result;
	}

	private boolean validateFName(String fname) {
		Pattern pattern = Pattern.compile(ValidationConstant.FIRST_NAME_PATTERN);
		Matcher matcher = pattern.matcher(fname);
		return matcher.matches();
	}

	private boolean validateLName(String lname) {
		Pattern pattern = Pattern.compile(ValidationConstant.LAST_NAME_PATTERN);
		Matcher matcher = pattern.matcher(lname);
		return matcher.matches();
	}

	private boolean validatePassport(String passport) {
		Pattern pattern = Pattern.compile(ValidationConstant.PASSPORT_PATTERN);
		Matcher matcher = pattern.matcher(passport);
		return matcher.matches();
	}

	private boolean validateNationality(String tempNationalityId, List<Nationality> nationalities) {
		for (Nationality nationality : nationalities) {
			if (nationality.getCountryId().equals(tempNationalityId)) {
				return true;
			}
		}
		return false;
	}

	private boolean validateCapacity(String capacity) {
		Pattern pattern = Pattern.compile(ValidationConstant.CAPACITY_PATTERN);
		Matcher matcher = pattern.matcher(capacity);
		return matcher.matches();
	}

	private boolean validateFromTo(String from, String to) {
		if (ValidationConstant.EMPTY_STRING.equals(from) || ValidationConstant.EMPTY_STRING.equals(to)) {
			return false;
		}
		LocalDate localDateFrom = LocalDate.parse(from);
		LocalDate localDateTo = LocalDate.parse(to);
		return (localDateFrom.isAfter(LocalDate.now())||localDateFrom.isEqual(LocalDate.now())) && (localDateFrom.isEqual(localDateTo)||localDateTo.isAfter(localDateFrom));
	}

}
