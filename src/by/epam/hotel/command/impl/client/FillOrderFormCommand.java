package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.Client;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;

public class FillOrderFormCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			int index = Integer.parseInt(request.getParameter("index"));
			Client client = sessionData.getClients().get(--index);
			request.setAttribute("tempFName", client.getFirstName());
			request.setAttribute("tempLName", client.getLastName());
			request.setAttribute("tempPassport", client.getPassport());
			request.setAttribute("tempNationality", client.getNationality());
			page = ConfigurationManager.getProperty("path.page.order");
			System.out.println(page);
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
