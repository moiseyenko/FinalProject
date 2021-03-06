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
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to send admin to page with all nationalities.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ToAllNationalitiesCommand implements ActionCommand {

	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}
	 * method will get list with all nationalities and will send admin by
	 * {@link by.epam.hotel.util.type.RouterType#FORWARD FORWARD} to page with
	 * received list. Otherwise method will return user by
	 * {@link by.epam.hotel.util.type.RouterType#FORWARD FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int currentPage = Integer.valueOf(request.getParameter(ParameterConstant.CURRENT_PAGE));
			int recordsPerPage = Integer.valueOf(request.getParameter(ParameterConstant.RECORDS_PER_PAGE));
			try {
				List<Nationality> nationalityList = AdminService.getNationalitiesList(currentPage, recordsPerPage);
				sessionData.setNationalityList(nationalityList);
				int rows = AdminService.getNumberOfRowsNationalities();
				int noOfPages = rows / recordsPerPage;
				if (rows % recordsPerPage > 0) {
					noOfPages++;
				}
				sessionData.setNoOfPages(noOfPages);
				sessionData.setCurrentPage(currentPage);
				sessionData.setRecordsPerPage(recordsPerPage);
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_NATIONALITIES);
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
