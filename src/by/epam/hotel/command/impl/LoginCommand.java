package by.epam.hotel.command.impl;

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
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class LoginCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(LoginCommand.class);
	private static final String PARAM_LOGIN = "login";
	private static final String PARAM_PASSWORD = "password";

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		//System.out.println("in LoginCommand " + sessionData.getRole());

		switch (sessionData.getRole()) {
		case GUEST:
			String login = request.getParameter(PARAM_LOGIN);
			String password = request.getParameter(PARAM_PASSWORD);
			try {
				if (LoginLogic.checkLogin(login, password)) {
					RoleType role = LoginLogic.checkRights(login);
					sessionData.setRole(role);
					sessionData.setLogin(login);
					if(role==RoleType.ADMIN) {
						page = ConfigurationManager.getProperty("path.page.adminmain");
					}else {
						page = ConfigurationManager.getProperty("path.page.clientmain");	
					}
					router.setPage(page);
					router.setType(RouterType.REDIRECT);
				} else {
					request.setAttribute("errorLoginPassMessage", MessageManager.getProrerty("message.loginerror"));
					page = ConfigurationManager.getProperty("path.page.login");
					router.setPage(page);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			} 
			break;
		case CLIENT:
			request.setAttribute("errorRelogMessage", MessageManager.getProrerty("message.relogerror"));
			page = ConfigurationManager.getProperty("path.page.clientmain");
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		case ADMIN:
			request.setAttribute("errorRelogMessage", MessageManager.getProrerty("message.relogerror"));
			page = ConfigurationManager.getProperty("path.page.adminmain");
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		}
		return router;
	}

}
