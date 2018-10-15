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
import by.epam.hotel.util.validator.AuthenticationValidator;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to finish client registration.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class CheckKeyAndSignUpCommand implements ActionCommand {

	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to client
	 * or admin home page respectively. If user's role equals to
	 * {@link by.epam.hotel.util.type.RoleType#GUEST GUEST}, confirmation key is
	 * correct and account with specified login or email does not exist, method will
	 * create new account and send client by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to client home page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		RoleType role = sessionData.getRole();
		switch (role) {
		case GUEST:
			String login = sessionData.getTempLogin();
			String password = sessionData.getTempPassword();
			String email = sessionData.getTempEmail();
			String emailKey = sessionData.getTempEmailKey();
			String emailConfirmationKey = request.getParameter(ParameterConstant.EMAIL_CONFIRMATION_KEY).trim();
			if (AuthenticationValidator.validateEmailConfirmationKey(emailConfirmationKey)
					&& emailKey.equals(emailConfirmationKey)) {
				try {
					if (CommonService.createAccount(login, email, password)) {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
						router.setPage(page);
						router.setType(RouterType.REDIRECT);
						sessionData.setLogin(login);
						sessionData.setRole(RoleType.CLIENT);
					} else {
						request.setAttribute(AttributeConstant.ERROR_SIGHUP_MESSAGE, MessageManager
								.getProrerty(PropertyConstant.MESSAGE_SIGNUP_ERROR, sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_KEY_CONFIRMATION_MESSAGE, MessageManager
						.getProrerty(PropertyConstant.MESSAGE_KEY_CONFIRMATION_ERROR, sessionData.getLocale()));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CONFIRMATION_EMAIL);
				router.setPage(page);
				router.setType(RouterType.FORWARD);
			}
			break;
		case CLIENT:
			request.setAttribute(AttributeConstant.ERROR_RE_SIGNUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_SIGHUP_ERROR, sessionData.getLocale()));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		case ADMIN:
			request.setAttribute(AttributeConstant.ERROR_RE_SIGNUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_SIGHUP_ERROR, sessionData.getLocale()));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		}
		return router;
	}

}
