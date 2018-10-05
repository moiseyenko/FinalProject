package by.epam.hotel.command.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class BackToSignupCommand implements ActionCommand{

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
