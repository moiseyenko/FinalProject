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
import by.epam.hotel.logic.ToAllNationalitiesLogic;
import by.epam.hotel.util.ConfigurationManager;

public class ToAllNationalitiesCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ToAllNationalitiesCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int currentPage = Integer.valueOf(request.getParameter("currentPage"));
			int recordsPerPage = Integer.valueOf(request.getParameter("recordsPerPage"));
			try {
				List<Nationality> nationalityList = ToAllNationalitiesLogic.getNationalitiesList(currentPage,
						recordsPerPage);
				sessionData.setNationalityList(nationalityList);
				int rows = ToAllNationalitiesLogic.getNumberOfRows();
				int noOfPages = rows / recordsPerPage;
				if (rows % recordsPerPage > 0) {
					noOfPages++;
				}
				sessionData.setNoOfPages(noOfPages);
				sessionData.setCurrentPage(currentPage);
				sessionData.setRecordsPerPage(recordsPerPage);
				page = ConfigurationManager.getProperty("path.page.allnationalities");
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
