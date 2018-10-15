package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to invalidating the current session and logging out.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class LogoutCommand implements ActionCommand {

	/**
	 * Method will invalidate current session, log out and send user to welcome
	 * page.
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		request.getSession().invalidate();
		Router router = new Router();
		String page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		router.setPage(page);
		router.setType(RouterType.REDIRECT);
		return router;
	}

}
