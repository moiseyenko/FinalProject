package by.epam.hotel.command.impl.client;

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
import by.epam.hotel.logic.ChangeLoginLogic;
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ChangeLoginCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(ChangeLoginCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			String newLogin = request.getParameter("newLogin");
			String tempPassword = request.getParameter("password");
			String currentLogin = sessionData.getLogin();
			if (validateLogin(newLogin)) {
				try {
					if (LoginLogic.checkLogin(currentLogin, tempPassword)) {
						if (ChangeLoginLogic.changeLogin(newLogin, currentLogin, tempPassword)) {
							sessionData.setLogin(newLogin);
							page = ConfigurationManager.getProperty("path.page.successchangelogin");
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute("errorChangeLoginMessage",
									MessageManager.getProrerty("message.changeloginerror"));
							page = ConfigurationManager.getProperty("path.page.changelogin");
							router.setType(RouterType.FORWARD);
						}
					} else {
						request.setAttribute("errorCheckLoginPasswordMessage", MessageManager.getProrerty("message.checkloginpassworderror"));
						page = ConfigurationManager.getProperty("path.page.changelogin");
						router.setPage(page);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			} else {
				request.setAttribute("errorLoginValidateMessage",
						MessageManager.getProrerty("message.loginvalidateerror"));
				page = ConfigurationManager.getProperty("path.page.changelogin");
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

	private boolean validateLogin(String login) {
		String LOGIN_PATTERN = "^[a-zA-ZР-пр-џ0-9_-]{3,25}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(login);
		return matcher.matches();
	}

}
