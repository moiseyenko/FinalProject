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
import by.epam.hotel.dao.entity.Account;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ChangeAdminRightsLogic;
import by.epam.hotel.logic.ToAllAccountsLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ChangeAdminRightsCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ChangeAdminRightsCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int accountIndex = Integer.parseInt(request.getParameter("accountIndex"));
			Account accountToChangeAdminRights = sessionData.getAccountList().get(--accountIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ChangeAdminRightsLogic.changeAccountAdminRights(accountToChangeAdminRights)) {
					List<Account> accountList = ToAllAccountsLogic.getAccountsList(currentPage,
							recordsPerPage);
					sessionData.setAccountList(accountList);
					page = ConfigurationManager.getProperty("path.page.allaccounts");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorChangeAdminRightsMessage", MessageManager.getProrerty("message.changeadminrightserror"));
					page = ConfigurationManager.getProperty("path.page.allaccounts");
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
