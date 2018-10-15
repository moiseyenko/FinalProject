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
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to return user back to signup page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class BackToSignupCommand implements ActionCommand{

	/**
	 * Method will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to singup page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		sessionData.setTempLogin(null);
		sessionData.setTempPassword(null);
		sessionData.setTempEmail(null);
		sessionData.setTempEmailKey(null);
		page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SIGNUP);
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
