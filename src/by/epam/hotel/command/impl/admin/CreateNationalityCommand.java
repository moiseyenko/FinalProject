package by.epam.hotel.command.impl.admin;

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
import by.epam.hotel.dao.entity.Nationality;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.CreateNationalityLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class CreateNationalityCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(CreateNationalityCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			String countryId = request.getParameter("countryId");
			String country = request.getParameter("country");
			if(validateInputData(countryId, country, request)) {
				Nationality newNationality = new Nationality(countryId, country);
					try {
						if(CreateNationalityLogic.createNationality(newNationality)) {
							page = ConfigurationManager.getProperty("path.page.successcreatenationality");
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute("errorCreateNationalityMessage", MessageManager.getProrerty("message.createnationalityerror"));
							page = ConfigurationManager.getProperty("path.page.createnationality");
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						LOG.error(e);
						throw new CommandException(e);
					}	
			}else {
				page = ConfigurationManager.getProperty("path.page.createnationality");
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateInputData(String countryId, String country, HttpServletRequest request) {
		boolean result = true;
		
		if (!validateCountryId(countryId)) {
			request.setAttribute("errorCountryIdMessage", MessageManager.getProrerty("message.countryiderror"));
			result = false;
		}
		if (!validateCountry(country)) {
			request.setAttribute("errorCountryMessage", MessageManager.getProrerty("message.countryerror"));
			result = false;
		}
		
		return result;
	}
	
	private boolean validateCountryId(String countryId) {
		String LOGIN_PATTERN = "^[A-Z][A-Z]$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(countryId);
		return matcher.matches();
	}
	
	private boolean validateCountry(String country) {
		String LOGIN_PATTERN = "^[A-Za-zÀ-ÿà-ÿ ¨¸'-]{1,80}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}

}
