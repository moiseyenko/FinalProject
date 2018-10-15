package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.MailException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.CommonService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.Encoder;
import by.epam.hotel.util.MailSender;
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
 * used to start client registration.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class SignUpCommand implements ActionCommand {	

	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to client
	 * or admin home page respectively. If user's role equals to
	 * {@link by.epam.hotel.util.type.RoleType#GUEST GUEST}, inputted login, password and 
	 * email are correct and account with specified login or email does not exist, method will
	 * send confirmation key to specified email and send user to key confirmation page.
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
			String login = request.getParameter(ParameterConstant.LOGIN);
			String password = request.getParameter(ParameterConstant.PASSWORD);
			String email = request.getParameter(ParameterConstant.EMAIL);

			if (validateInputData(login, password, email, request, sessionData)) {
				try {
					String emailKey = Encoder.generateEmailKey(email);
					if(!CommonService.checkAccount(login, email)) {
						try {
							if(MailSender.sendSingUpConfirmationEmail(email, emailKey, sessionData.getLocale())) {
								sessionData.setTempLogin(login);
								sessionData.setTempPassword(password);
								sessionData.setTempEmail(email);
								sessionData.setTempEmailKey(emailKey);
								page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CONFIRMATION_EMAIL);
								router.setPage(page);
								router.setType(RouterType.REDIRECT);
							}else {
								request.setAttribute(AttributeConstant.ERROR_SEND_CONFIRMATION_EMAIL_MESSAGE,
										MessageManager.getProrerty(PropertyConstant.MESSAGE_SEND_CONFIRMATION_EMAIL_ERROR,
												sessionData.getLocale()));
								page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
								router.setPage(page);
								router.setType(RouterType.FORWARD);
							}
						} catch (MailException e) {
							throw new CommandException(e);
						}
					}else {
						request.setAttribute(AttributeConstant.ERROR_SIGHUP_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_SIGNUP_ERROR, sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
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

	private boolean validateInputData(String login, String password, String email, HttpServletRequest request, SessionData sessionData) {
		boolean result = true;
		if (!AccountValidator.validateLogin(login)) {
			request.setAttribute(AttributeConstant.ERROR_LOGIN_SIGNUP_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_LOGIN_SIGNUP_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!AccountValidator.validateEmail(email)) {
			request.setAttribute(AttributeConstant.ERROR_EMAIL_SIGHUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_EMAIL_SIGHUP_ERROR, sessionData.getLocale()));
			result = false;
		}
		if (!AccountValidator.validatePassword(password)) {
			request.setAttribute(AttributeConstant.ERROR_PASSWORD_SIGHUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSWORD_SIGNUP_ERROR, sessionData.getLocale()));
			result = false;
		}
		return result;
	}

}
