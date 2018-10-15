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
import by.epam.hotel.util.validator.AccountValidator;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to change login of current user.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeLoginCommand implements ActionCommand {

	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome
	 * page. If new login is incorrect or new login already exists or new login can
	 * not be changed, method will return back client or admin to previous page with
	 * according information. Otherwise method will change login of current user and
	 * send him by {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with 
	 * successfull change information.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT || sessionData.getRole() == RoleType.ADMIN) {
			String newLogin = request.getParameter(ParameterConstant.NEW_LOGIN);
			String tempPassword = request.getParameter(ParameterConstant.PASSWORD);
			String currentLogin = sessionData.getLogin();
			if (AccountValidator.validateLogin(newLogin)) {
				try {
					if (CommonService.checkLogin(currentLogin, tempPassword)) {
						if (CommonService.changeLogin(newLogin, currentLogin)) {
							sessionData.setLogin(newLogin);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CHANGE_LOGIN);
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_LOGIN_MESSAGE, MessageManager
									.getProrerty(PropertyConstant.MESSAGE_CHANGE_LOGIN_ERROR, sessionData.getLocale()));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_LOGIN);
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute(AttributeConstant.ERROR_CHECK_LOGIN_PASSWORD_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_CHECK_LOGIN_PASSWORD_ERROR,
										sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_LOGIN);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_LOGIN_VALIDATE_MESSAGE, MessageManager
						.getProrerty(PropertyConstant.MESSAGE_LOGIN_VALIDATE_ERROR, sessionData.getLocale()));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_LOGIN);
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
