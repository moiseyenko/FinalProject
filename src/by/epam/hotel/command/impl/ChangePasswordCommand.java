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
import by.epam.hotel.logic.ChangePasswordLogic;
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ChangePasswordCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(ChangePasswordCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT||sessionData.getRole() == RoleType.ADMIN) {
			String newPassword = request.getParameter("newPassword");
			String oldPassword = request.getParameter("oldPassword");
			String currentLogin = sessionData.getLogin();
			if (validatePassword(newPassword)) {
				try {
					if (LoginLogic.checkLogin(currentLogin, oldPassword)) {
						if (ChangePasswordLogic.changePassword(oldPassword, newPassword, currentLogin)) {
							page = ConfigurationManager.getProperty("path.page.successchangepassword");
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute("errorChangePasswordMessage",
									MessageManager.getProrerty("message.changepassworderror"));
							page = ConfigurationManager.getProperty("path.page.changepassword");
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute("errorCheckLoginPasswordMessage",
								MessageManager.getProrerty("message.checkloginpassworderror"));
						page = ConfigurationManager.getProperty("path.page.changepassword");
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			} else {
				request.setAttribute("errorPasswordValidateMessage",
						MessageManager.getProrerty("message.passwordvalidateerror"));
				page = ConfigurationManager.getProperty("path.page.changepassword");
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
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
		String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-zà-ÿ])(?=.*[A-ZÀ-ß])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";
		Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
		Matcher matcher = pattern.matcher(password);
		return matcher.matches();
	}

}
