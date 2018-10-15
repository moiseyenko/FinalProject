package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RouterType;


/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to return user back to welcome page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class SignupBackCommand implements ActionCommand {

	/**
	 * Method will return user to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) {
		Router router = new Router();
		router.setType(RouterType.FORWARD);
		String page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		router.setPage(page);
		return router;
	}
}
