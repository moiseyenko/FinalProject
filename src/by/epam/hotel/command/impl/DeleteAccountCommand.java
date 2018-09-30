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
import by.epam.hotel.logic.DeleteAccountLogic;
import by.epam.hotel.logic.LoginLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class DeleteAccountCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(DeleteAccountCommand.class);
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			String password = request.getParameter("password");
			String currentLogin = sessionData.getLogin();
			try {
				if (LoginLogic.checkLogin(currentLogin, password)) {
					if(DeleteAccountLogic.deleteAccount(currentLogin)) {
						System.out.println("before invalidate");
						request.getSession().invalidate();
						System.out.println("after invalidate");
						page = ConfigurationManager.getProperty("path.page.successdeleteaccount");
						router.setType(RouterType.REDIRECT);
					} else {
						request.setAttribute("errorDeleteAccountMessage",
								MessageManager.getProrerty("message.deleteaccounterror"));
						page = ConfigurationManager.getProperty("path.page.deleteaccount");
						router.setType(RouterType.FORWARD);
					}	
				} else {
					request.setAttribute("errorCheckLoginPasswordMessage",
							MessageManager.getProrerty("message.checkloginpassworderror"));
					page = ConfigurationManager.getProperty("path.page.deleteaccount");
					router.setPage(page);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}	
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
