package by.epam.hotel.command.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.SignUpLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

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
			String emailConfirmationKey = request.getParameter("emailConfirmationKey").trim();
			if (validateEmailConfirmationKey(emailConfirmationKey)&&emailKey.equals(emailConfirmationKey)) {
				try {
					if (SignUpLogic.createAccount(login, email, password)) {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
						router.setPage(page);
						router.setType(RouterType.REDIRECT);
						// maybe need getSession(false)
						sessionData.setLogin(login);
						sessionData.setRole(RoleType.CLIENT);
					}else {
						request.setAttribute(AttributeConstant.ERROR_SIGHUP_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_SIGNUP_ERROR));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute("errorKeyConfirmationMessage",
						MessageManager.getProrerty("message.keyconfirmationerror"));
				page = ConfigurationManager.getProperty("path.page.confirmationemail");
				router.setPage(page);
				router.setType(RouterType.FORWARD);
			}
			break;
		case CLIENT:
			request.setAttribute(AttributeConstant.ERROR_RE_SIGNUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_SIGHUP_ERROR));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		case ADMIN:
			request.setAttribute(AttributeConstant.ERROR_RE_SIGNUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_SIGHUP_ERROR));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		}
		return router;
	}

	private boolean validateEmailConfirmationKey(String emailConfirmationKey) {
		Pattern pattern = Pattern.compile("^[0-9a-f]{10}$");
		Matcher matcher = pattern.matcher(emailConfirmationKey);
		return matcher.matches();
	}
	
	

}
