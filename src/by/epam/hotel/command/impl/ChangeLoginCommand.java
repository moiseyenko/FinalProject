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
import by.epam.hotel.logic.ChangeLoginLogic;
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class ChangeLoginCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT||sessionData.getRole() == RoleType.ADMIN) {
			String newLogin = request.getParameter(ParameterConstant.NEW_LOGIN);
			String tempPassword = request.getParameter(ParameterConstant.PASSWORD);
			String currentLogin = sessionData.getLogin();
			if (validateLogin(newLogin)) {
				try {
					if (LoginLogic.checkLogin(currentLogin, tempPassword)) {
						if (ChangeLoginLogic.changeLogin(newLogin, currentLogin, tempPassword)) {
							sessionData.setLogin(newLogin);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CHANGE_LOGIN);
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute(AttributeConstant.ERROR_CHANGE_LOGIN_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_LOGIN_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_LOGIN);
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute(AttributeConstant.ERROR_CHECK_LOGIN_PASSWORD_MESSAGE, 
								MessageManager.getProrerty(PropertyConstant.MESSAGE_CHECK_LOGIN_PASSWORD_ERROR));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_LOGIN);
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_LOGIN_VALIDATE_MESSAGE,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_LOGIN_VALIDATE_ERROR));
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

	private boolean validateLogin(String login) {
		Pattern pattern = Pattern.compile(ValidationConstant.LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(login);
		return matcher.matches();
	}

}
