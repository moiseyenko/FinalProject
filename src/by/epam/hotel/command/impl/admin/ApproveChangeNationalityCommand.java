package by.epam.hotel.command.impl.admin;

import java.util.List;
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
import by.epam.hotel.logic.ApproveChangeNationalityLogic;
import by.epam.hotel.logic.ToAllNationalitiesLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class ApproveChangeNationalityCommand implements ActionCommand{
	
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
			if(validateCountry(country)) {
					Nationality updatedNationality = new Nationality(countryId, country);
					try {
						if(ApproveChangeNationalityLogic.changeNationality(updatedNationality)) {
							List<Nationality> nationalityList = ToAllNationalitiesLogic.getNationalitiesList(currentPage,
									recordsPerPage);
							sessionData.setNationalityList(nationalityList);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_NATIONALITIES);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_NATIONALITY_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_NATIONALITY_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_NATIONALITY);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
			}else {
				request.setAttribute(AttributeConstant.ERROR_COUNTRY_MESSAGE, 
						MessageManager.getProrerty(PropertyConstant.MESSAGE_COUNTRY_ERROR));
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
	
	private boolean validateCountry(String country) {
		Pattern pattern = Pattern.compile(ValidationConstant.COUNTRY_PATTERN);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}

}
