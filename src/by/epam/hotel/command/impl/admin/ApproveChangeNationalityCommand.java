package by.epam.hotel.command.impl.admin;

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
import by.epam.hotel.dao.entity.Nationality;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ApproveChangeNationalityLogic;
import by.epam.hotel.logic.ToAllNationalitiesLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ApproveChangeNationalityCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ApproveChangeNationalityCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			String countryId = sessionData.getNationalityToChange().getCountryId();
			String country = request.getParameter("country");
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			if(validateCountry(country)) {
					Nationality updatedNationality = new Nationality(countryId, country);
					try {
						if(ApproveChangeNationalityLogic.changeNationality(updatedNationality)) {
							List<Nationality> nationalityList = ToAllNationalitiesLogic.getNationalitiesList(currentPage,
									recordsPerPage);
							sessionData.setNationalityList(nationalityList);
							page = ConfigurationManager.getProperty("path.page.allnationalities");
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute("errorChangeNationalityMessage", MessageManager.getProrerty("message.changenationalityerror"));
							page = ConfigurationManager.getProperty("path.page.changenationality");
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						LOG.error(e);
						throw new CommandException(e);
					}
			}else {
				request.setAttribute("errorCountryMessage", MessageManager.getProrerty("message.countryerror"));
				page = ConfigurationManager.getProperty("path.page.changenationality");
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateCountry(String country) {
		String LOGIN_PATTERN = "^[A-Za-zÀ-ÿà-ÿ ¨¸'-]{1,80}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(country);
		return matcher.matches();
	}

}
