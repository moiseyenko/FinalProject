package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.CommonService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

public class DeleteAccountCommand implements ActionCommand {
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT||sessionData.getRole() == RoleType.ADMIN) {
			String password = request.getParameter(ParameterConstant.PASSWORD);
			String currentLogin = sessionData.getLogin();
			try {
				if (CommonService.checkLogin(currentLogin, password)) {
					if(CommonService.deleteAccount(currentLogin)) {
						request.getSession().invalidate();
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_DELETE_ACCOUNT);
						router.setType(RouterType.REDIRECT);
					} else {
						request.setAttribute(AttributeConstant.ERROR_DELETE_ACCOUNT_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_DELETE_ACCOUNT_ERROR, sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_DELETE_ACCOUNT);
						router.setType(RouterType.FORWARD);
					}	
				} else {
					request.setAttribute(AttributeConstant.ERROR_CHECK_LOGIN_PASSWORD_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_CHECK_LOGIN_PASSWORD_ERROR, sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_DELETE_ACCOUNT);
					router.setPage(page);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
