package by.epam.hotel.command.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.SignUpLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class SignUpCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(SignUpCommand.class);
	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_PASSWORD = "password";
	private static final String PARAM_EMAIL = "email";

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		RoleType role = sessionData.getRole();
		switch (role) {
		case GUEST:
			String login = request.getParameter(PARAM_LOGIN);
			String password = request.getParameter(PARAM_PASSWORD);
			String email = request.getParameter(PARAM_EMAIL);

			if (validateInputData(login, password, email, request)) {
				try {
					if (SignUpLogic.createAccount(login, email, password)) {
						page = ConfigurationManager.getProperty("path.page.clientmain");
						router.setPage(page);
						router.setType(RouterType.REDIRECT);
						// maybe need getSession(false)
						sessionData.setLogin(login);
						sessionData.setRole(RoleType.CLIENT);
						sessionData.setInnerRedirect(true);
						System.out.println("!!!!!!!!!");
						System.out.println("!!!in signupcommmand!!!!!!");
						System.out.println("!!!!!!!!!!!!");
						// session.setAttribute("sessionData", sessionData);
					} else {
						request.setAttribute("errorSignupMessage", MessageManager.getProrerty("message.signuperror"));
						page = ConfigurationManager.getProperty("path.page.signup");
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			} else {
				page = ConfigurationManager.getProperty("path.page.signup");
				router.setPage(page);
				router.setType(RouterType.FORWARD);
			}
			break;
		case CLIENT:
			request.setAttribute("errorReSignupMessage", MessageManager.getProrerty("message.resignuperror"));
			page = ConfigurationManager.getProperty("path.page.clientmain");
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		case ADMIN:
			// TODO admin
			break;
		}
		return router;
	}

	private boolean validateInputData(String login, String password, String email, HttpServletRequest request) {
		boolean result = true;
		if (!validateLogin(login)) {
			request.setAttribute("errorLoginSignupMessage", MessageManager.getProrerty("message.loginsignuperror"));
			result = false;
		}
		if (!validateEmail(email)) {
			request.setAttribute("errorEmailSignupMessage", MessageManager.getProrerty("message.emailsignuperror"));
			result = false;
		}
		if (!validatePassword(password)) {
			request.setAttribute("errorPasswordSignupMessage",
					MessageManager.getProrerty("message.passwordsignuperror"));
			result = false;
		}
		return result;
	}

	private boolean validateLogin(String login) {
		String LOGIN_PATTERN = "^[a-zA-ZÀ-ßà-ÿ0-9_-]{3,25}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(login);
		return matcher.matches();
	}

	private boolean validateEmail(String email) {
		String EMAIL_PATTERN = "^[A-Z0-9_.%+-]{1,30}@[A-Z0-9.-]{1,10}\\.[A-Z]{2,6}$";
		Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(email);
		return matcher.matches();
	}

	/*
	 * ^ # start-of-string (?=.*[0-9]) # a digit must occur at least once
	 * (?=.*[a-z]) # a lower case letter must occur at least once (?=.*[A-Z]) # an
	 * upper case letter must occur at least once (?=.*[@#$%^&+=]) # a special
	 * character must occur at least once (?=\S+$) # no whitespace allowed in the
	 * entire string .{8,} # anything, at least eight places though $ #
	 * end-of-string
	 */
	private boolean validatePassword(String password) {
		String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zà-ÿ])(?=.*[A-ZÀ-ß])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
