package by.epam.hotel.command.impl.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.CreateNationalityLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class CreateNationalityCommand implements ActionCommand{
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String countryId = request.getParameter(ParameterConstant.COUNTRY_ID);
			String country = request.getParameter(ParameterConstant.COUNTRY);
			if(validateInputData(countryId, country, request)) {
				Nationality newNationality = new Nationality(countryId, country);
					try {
						if(CreateNationalityLogic.createNationality(newNationality)) {
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CREATE_NATIONALITY);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CREATE_NATIONALITY_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CREATE_NATIONALITY_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_NATIONALITY);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}	
			}else {
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_NATIONALITY);
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateInputData(String countryId, String country, HttpServletRequest request) {
		boolean result = true;
		
		if (!validateCountryId(countryId)) {
			request.setAttribute(AttributeConstant.ERROR_COUNTRY_ID_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ID_ERROR));
			result = false;
		}
		if (!validateCountry(country)) {
			request.setAttribute(AttributeConstant.ERROR_COUNTRY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ERROR));
			result = false;
		}
		
		return result;
	}
	
	private boolean validateCountryId(String countryId) {
		Pattern pattern = Pattern.compile(ValidationConstant.COUNTRY_ID_PATTERN);
		Matcher matcher = pattern.matcher(countryId);
		return matcher.matches();
	}
	
	private boolean validateCountry(String country) {
		Pattern pattern = Pattern.compile(ValidationConstant.COUNTRY_PATTERN);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}

}
