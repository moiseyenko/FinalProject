package by.epam.hotel.command.impl.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import by.epam.hotel.util.constant.ValidationConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

public class CheckKeyAndSignUpCommand implements ActionCommand{

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
			if (validateEmailConfirmationKey(emailConfirmationKey)&&emailKey.equals(emailConfirmationKey)) {
				try {
					if (CommonService.createAccount(login, email, password)) {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
						router.setPage(page);
						router.setType(RouterType.REDIRECT);
						sessionData.setLogin(login);
						sessionData.setRole(RoleType.CLIENT);
					}else {
						request.setAttribute(AttributeConstant.ERROR_SIGHUP_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_SIGNUP_ERROR,
										sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_KEY_CONFIRMATION_MESSAGE,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_KEY_CONFIRMATION_ERROR,
								sessionData.getLocale()));
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

	private boolean validateEmailConfirmationKey(String emailConfirmationKey) {
		Pattern pattern = Pattern.compile(ValidationConstant.CONFIRMATION_KEY);
		Matcher matcher = pattern.matcher(emailConfirmationKey);
		return matcher.matches();
	}
	
	

}
