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
 * used to change password of current user.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangePasswordCommand implements ActionCommand {

	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome
	 * page. If new password is incorrect or current user does not exist or new password can
	 * not be changed, method will return back client or admin to previous page with
	 * according information. Otherwise method will change password of current user and
	 * send him by {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with 
	 * successfull change information.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT||sessionData.getRole() == RoleType.ADMIN) {
			String newPassword = request.getParameter(ParameterConstant.NEW_PASSWORD);
			String oldPassword = request.getParameter(ParameterConstant.OLD_PASSWORD);
			String currentLogin = sessionData.getLogin();
			if (AccountValidator.validatePassword(newPassword)) {
				try {
					if (CommonService.checkLogin(currentLogin, oldPassword)) {
						if (CommonService.changePassword(newPassword, currentLogin)) {
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CHANGE_PASSWORD);
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_PASSWORD_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_PASSWORD_ERROR, sessionData.getLocale()));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PASSWORD);
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute(AttributeConstant.ERROR_CHAECK_LOGIN_PASSWORD_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_CHECK_LOGIN_PASSWORD_ERROR, sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PASSWORD);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_PASSWORD_VALIDATE_MESSAGE,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSWORD_VALIDATE_ERROR, sessionData.getLocale()));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PASSWORD);
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
