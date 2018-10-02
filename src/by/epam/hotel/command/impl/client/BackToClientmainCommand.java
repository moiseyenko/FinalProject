package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.util.ConfigurationManager;

public class BackToClientmainCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) {
		System.out.println("in BackToClientmainCommand " + request.getSession().getAttribute("sessionData"));
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			page = ConfigurationManager.getProperty("path.page.clientmain");
		} else {
			page = ConfigurationManager.getProperty("path.page.login");
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
