package by.epam.hotel.command.impl;

import java.time.LocalDate;
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
import by.epam.hotel.dao.entity.Client;
import by.epam.hotel.dao.entity.Nationality;
import by.epam.hotel.dao.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.FindRoomLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class FindRoomCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(FindRoomCommand.class);
	private final String PARAM_FNAME = "fname";
	private final String PARAM_LNAME = "lname";
	private final String PARAM_PASSPORT = "passport";
	private final String PARAM_NATIONATILY = "nationality";
	private final String PARAM_CLASS = "class";
	private final String PARAM_CAPACITY = "capacity";
	private final String PARAM_FROM = "from";
	private final String PARAM_TO = "to";

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			String fname = request.getParameter(PARAM_FNAME);
			String lname = request.getParameter(PARAM_LNAME);
			String passport = request.getParameter(PARAM_PASSPORT);
			String nationality = request.getParameter(PARAM_NATIONATILY);
			String roomClass = request.getParameter(PARAM_CLASS);
			String capacity = request.getParameter(PARAM_CAPACITY);
			String from = request.getParameter(PARAM_FROM);
			String to = request.getParameter(PARAM_TO);
			List<Nationality> nationalities = sessionData.getNationalities();
			if (validateInputData(fname, lname, passport, nationality, nationalities, capacity, from, to, request)) {
				try {
					Client chosenClient = new Client(fname, lname, passport, nationality);
					if (!FindRoomLogic.checkClientInBlacklist(chosenClient)) {
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
						page = ConfigurationManager.getProperty("path.page.availableroom");
					} else {
						request.setAttribute("errorBlackListClientMessage",
								MessageManager.getProrerty("message.blacklistclient"));
						page = ConfigurationManager.getProperty("path.page.order");
					}
				} catch (ServiceException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			} else {
				page = ConfigurationManager.getProperty("path.page.order");
				router.setPage(page);
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

	private boolean validateInputData(String fname, String lname, String passport, String nationality,
			List<Nationality> nationalities, String capacity, String from, String to, HttpServletRequest request) {
		boolean result = true;
		if (!validateFName(fname)) {
			request.setAttribute("errorFNameMessage", MessageManager.getProrerty("message.fnameerror"));
			result = false;
		}
		if (!validateLName(lname)) {
			request.setAttribute("errorLNameMessage", MessageManager.getProrerty("message.lnameerror"));
			result = false;
		}
		if (!validatePassport(passport)) {
			request.setAttribute("errorPassportMessage", MessageManager.getProrerty("message.passporterror"));
			result = false;
		}
		if (!validateNationality(nationality, nationalities)) {
			request.setAttribute("errorNationalityMessage", MessageManager.getProrerty("message.nationalityerror"));
			result = false;
		}
		if (!validateCapacity(capacity)) {
			request.setAttribute("errorCapacityMessage", MessageManager.getProrerty("message.capacityerror"));
			result = false;
		}
		if (!validateFromTo(from, to)) {
			request.setAttribute("errorFromToMessage", MessageManager.getProrerty("message.fromtoerror"));
			result = false;
		}
		return result;
	}

	private boolean validateFName(String fname) {
		String LOGIN_PATTERN = "^[A-ZÀ-ß¨][a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,44}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(fname);
		return matcher.matches();
	}

	private boolean validateLName(String lname) {
		String LOGIN_PATTERN = "^[a-zA-ZÀ-ßà-ÿ¨¸\\\\'.-]{1,45}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(lname);
		return matcher.matches();
	}

	private boolean validatePassport(String passport) {
		String LOGIN_PATTERN = "^[a-zA-Z0-9]{1,15}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
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
		String LOGIN_PATTERN = "^[0-9]{1,5}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(capacity);
		return matcher.matches();
	}

	private boolean validateFromTo(String from, String to) {
		if ("".equals(from) || "".equals(to)) {
			return false;
		}
		LocalDate localDateFrom = LocalDate.parse(from);
		LocalDate localDateTo = LocalDate.parse(to);
		return (localDateFrom.isAfter(LocalDate.now()) && (localDateFrom.isEqual(localDateTo)||localDateTo.isAfter(localDateFrom)));
	}

}
