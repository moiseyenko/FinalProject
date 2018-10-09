package by.epam.hotel.command.impl.admin;

import java.util.HashSet;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.AdminService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

public class ToAllEmailsCommand implements ActionCommand{

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
				List<Account> accountList = AdminService.getAccountsList(currentPage,
						recordsPerPage);
				sessionData.setAccountList(accountList);
				int rows = AdminService.getNumberOfRowsAccounts();
				int noOfPages = rows / recordsPerPage;
				if (rows % recordsPerPage > 0) {
					noOfPages++;
				}
				sessionData.setSendList(new HashSet<>());
				sessionData.setNoOfPages(noOfPages);
				sessionData.setCurrentPage(currentPage);
				sessionData.setRecordsPerPage(recordsPerPage);
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_EMAILS);
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
