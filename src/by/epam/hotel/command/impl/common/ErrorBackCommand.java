package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to send user after application error to home page depending on user's
 * role.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ErrorBackCommand implements ActionCommand {

	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT}, method will send client to client home page. 
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN
	 * ADMIN}, method will send admin to admin home page.
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#GUEST
	 * GUEST} method will send user to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		System.out.println("ROLE:" + sessionData.getRole());
		switch (sessionData.getRole()) {
		case GUEST:
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			break;
		case CLIENT:
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
			break;
		case ADMIN:
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
			break;
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
