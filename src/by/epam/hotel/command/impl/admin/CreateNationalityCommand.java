package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Nationality;
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
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;
import by.epam.hotel.util.validator.ClientValidator;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to creation of new nationality.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class CreateNationalityCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If parameters: countryId and country are invalid or if new nationality cannot be created, 
	 * method will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will create new nationality and send admin by
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
			String countryId = request.getParameter(ParameterConstant.COUNTRY_ID);
			String country = request.getParameter(ParameterConstant.COUNTRY);
			if(validateInputData(countryId, country, request, sessionData)) {
				Nationality newNationality = new Nationality(countryId, country);
					try {
						if(AdminService.createNationality(newNationality)) {
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CREATE_NATIONALITY);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CREATE_NATIONALITY_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CREATE_NATIONALITY_ERROR, sessionData.getLocale()));
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
	
	private boolean validateInputData(String countryId, String country, HttpServletRequest request, SessionData sessionData) {
		boolean result = true;
		
		if (!ClientValidator.validateCountryId(countryId)) {
			request.setAttribute(AttributeConstant.ERROR_COUNTRY_ID_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ID_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!ClientValidator.validateCountry(country)) {
			request.setAttribute(AttributeConstant.ERROR_COUNTRY_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ERROR, sessionData.getLocale()));
			result = false;
		}
		
		return result;
	}

}
