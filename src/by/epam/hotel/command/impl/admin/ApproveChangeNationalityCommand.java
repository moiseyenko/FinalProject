package by.epam.hotel.command.impl.admin;

import java.util.List;

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
 * and is used to approve modification of specified nationality.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ApproveChangeNationalityCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If parameter: country is invalid or if the nationality with specified parameter 
	 * cannot be updated, method will return user by 
	 * {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will approve modification of specified nationality and return admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with all nationalities.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String countryId = sessionData.getNationalityToChange().getCountryId();
			String country = request.getParameter(ParameterConstant.COUNTRY);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			if(ClientValidator.validateCountry(country)) {
					Nationality updatedNationality = new Nationality(countryId, country);
					try {
						if(AdminService.approveChangeNationality(updatedNationality)) {
							List<Nationality> nationalityList = AdminService.getNationalitiesList(currentPage,
									recordsPerPage);
							sessionData.setNationalityList(nationalityList);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_NATIONALITIES);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_NATIONALITY_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_NATIONALITY_ERROR, sessionData.getLocale()));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_NATIONALITY);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
			}else {
				request.setAttribute(AttributeConstant.ERROR_COUNTRY_MESSAGE, 
						MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ERROR, sessionData.getLocale()));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_NATIONALITY);
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
