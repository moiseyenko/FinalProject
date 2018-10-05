package by.epam.hotel.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ChangeAdminRightsLogic;
import by.epam.hotel.logic.ToAllAccountsLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class ChangeAdminRightsCommand implements ActionCommand{
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int accountIndex = Integer.parseInt(request.getParameter(ParameterConstant.ACCOUNT_INDEX));
			Account accountToChangeAdminRights = sessionData.getAccountList().get(--accountIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ChangeAdminRightsLogic.changeAccountAdminRights(accountToChangeAdminRights)) {
					List<Account> accountList = ToAllAccountsLogic.getAccountsList(currentPage,
							recordsPerPage);
					sessionData.setAccountList(accountList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_ACCOUNTS);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_CHANGE_ADMIN_RIGHTS_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_ADMIN_RIGHTS_ERROR));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_ACCOUNTS);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		return router;
	}

}
