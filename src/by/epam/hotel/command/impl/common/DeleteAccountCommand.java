package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
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

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to delete account of current user.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class DeleteAccountCommand implements ActionCommand {
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome
	 * page. If current user does not exist or current account can
	 * not be deleted, method will return back client or admin to previous page with
	 * according information. Otherwise method will delelte account of current user and
	 * send him by {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with 
	 * successfull deletion information.
	 */
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
