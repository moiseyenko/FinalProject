package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to send user to page with information about hotel.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class HotelInfoCommand implements ActionCommand {

	/**
	 * Send user to page with information about hotel.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = ConfigurationManager.getProperty(PropertyConstant.PAGE_HOTEL_INFO);
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
