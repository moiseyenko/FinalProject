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
import by.epam.hotel.logic.ChangePasswordLogic;
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class ChangePasswordCommand implements ActionCommand {

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
			if (validatePassword(newPassword)) {
				try {
					if (LoginLogic.checkLogin(currentLogin, oldPassword)) {
						if (ChangePasswordLogic.changePassword(oldPassword, newPassword, currentLogin)) {
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CHANGE_PASSWORD);
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_PASSWORD_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_PASSWORD_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PASSWORD);
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute(AttributeConstant.ERROR_CHAECK_LOGIN_PASSWORD_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_CHECK_LOGIN_PASSWORD_ERROR));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_PASSWORD);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_PASSWORD_VALIDATE_MESSAGE,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_PASSWORD_VALIDATE_ERROR));
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
