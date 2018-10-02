package by.epam.hotel.command.impl.admin;

import java.util.List;

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
import by.epam.hotel.logic.ChangeNationalityRemovedLogic;
import by.epam.hotel.logic.ToAllNationalitiesLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ChangeNationalityRemovedCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ChangeNationalityRemovedCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int nationalityIndex = Integer.parseInt(request.getParameter("nationalityIndex"));
			Nationality nationalityToChangeRemoved = sessionData.getNationalityList().get(--nationalityIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ChangeNationalityRemovedLogic.changeNationalityRemoved(nationalityToChangeRemoved)) {
					List<Nationality> nationalityList = ToAllNationalitiesLogic.getNationalitiesList(currentPage,
							recordsPerPage);
					sessionData.setNationalityList(nationalityList);
					page = ConfigurationManager.getProperty("path.page.allnationalities");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorChangeNationalityRemovedMessage", MessageManager.getProrerty("message.changenationalityremovederror"));
					page = ConfigurationManager.getProperty("path.page.allnationalities");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setPage(page);
		return router;
	}

}
