package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Client;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class FillOrderFormCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			int clientIndex = Integer.parseInt(request.getParameter(ParameterConstant.CLIENT_INDEX));
			Client client = sessionData.getClients().get(--clientIndex);
			request.setAttribute(AttributeConstant.TEMP_FIRST_NAME, client.getFirstName());
			request.setAttribute(AttributeConstant.TEMP_LAST_NAME, client.getLastName());
			request.setAttribute(AttributeConstant.TEMP_PASSPORT, client.getPassport());
			request.setAttribute(AttributeConstant.TEMP_NATIONALITY, client.getNationality());
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
			System.out.println(page);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
