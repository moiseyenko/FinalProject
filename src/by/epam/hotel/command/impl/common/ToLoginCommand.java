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
 * used send to login page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ToLoginCommand implements ActionCommand {

	/**
	 * Send user to login page.
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		String page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;

	}

}
