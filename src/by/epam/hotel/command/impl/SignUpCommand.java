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
import by.epam.hotel.logic.NewSignUpLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.Encoder;
import by.epam.hotel.util.MailException;
import by.epam.hotel.util.MailSender;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class SignUpCommand implements ActionCommand {	

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

			if (validateInputData(login, password, email, request)) {
				try {
					String emailKey = Encoder.generateEmailKey(email);
					if(!NewSignUpLogic.checkAccount(login, email)) {
						try {
							if(MailSender.sendSingUpConfirmationEmail(email, emailKey)) {
								sessionData.setTempLogin(login);
								sessionData.setTempPassword(password);
								sessionData.setTempEmail(email);
								sessionData.setTempEmailKey(emailKey);
								page = ConfigurationManager.getProperty("path.page.confirmationemail");
								router.setPage(page);
								router.setType(RouterType.REDIRECT);
							}else {
								request.setAttribute("errorSendConfirmationEmailMessage",
										MessageManager.getProrerty("message.sendconfirmationemailerror"));
								page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
								router.setPage(page);
								router.setType(RouterType.FORWARD);
							}
						} catch (MailException e) {
							throw new CommandException(e);
						}
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
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
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

	private boolean validateInputData(String login, String password, String email, HttpServletRequest request) {
		boolean result = true;
		if (!validateLogin(login)) {
			request.setAttribute(AttributeConstant.ERROR_LOGIN_SIGNUP_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_LOGIN_SIGNUP_ERROR));
			result = false;
		}
		if (!validateEmail(email)) {
			request.setAttribute(AttributeConstant.ERROR_EMAIL_SIGHUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_EMAIL_SIGHUP_ERROR));
			result = false;
		}
		if (!validatePassword(password)) {
			request.setAttribute(AttributeConstant.ERROR_PASSWORD_SIGHUP_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSWORD_SIGNUP_ERROR));
			result = false;
		}
		return result;
	}

	private boolean validateLogin(String login) {
		Pattern pattern = Pattern.compile(ValidationConstant.LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(login);
		return matcher.matches();
	}

	private boolean validateEmail(String email) {
		Pattern pattern = Pattern.compile(ValidationConstant.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
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
		Pattern pattern = Pattern.compile(ValidationConstant.PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
