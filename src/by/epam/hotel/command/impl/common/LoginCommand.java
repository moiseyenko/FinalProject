package by.epam.hotel.command.impl.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.CommonService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to login user.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class LoginCommand implements ActionCommand {
	private final static String SUPERLOGIN = "superadmin";
	private final static String SUPERPASSWORD = "superadmin";
	
	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} or {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will
	 * return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to client
	 * or admin home page respectively.
	 * If inputted login exists and passord is correct method will send user to client
	 * or admin home page depending on his role.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);

		switch (sessionData.getRole()) {
		case GUEST:
			String login = request.getParameter(ParameterConstant.LOGIN);
			String password = request.getParameter(ParameterConstant.PASSWORD);
			try {
				if (CommonService.checkLogin(login, password)) {
					RoleType role = CommonService.checkRights(login);
					sessionData.setRole(role);
					sessionData.setLogin(login);
					if(role==RoleType.ADMIN) {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
					}else {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);	
					}
					router.setPage(page);
					router.setType(RouterType.REDIRECT);
				}else if(SUPERLOGIN.equals(login)&&SUPERPASSWORD.equals(password)) {
					sessionData.setRole(RoleType.ADMIN);
					sessionData.setLogin(login);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
					router.setPage(page);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_LOGIN_PASS_MESSAGE, 
							MessageManager.getProrerty(PropertyConstant.MESSAGE_LOGIN_ERROR, sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_LOGIN);
					router.setPage(page);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			} 
			break;
		case CLIENT:
			request.setAttribute(AttributeConstant.ERROR_RELOG_MESSAGE, 
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_LOGIN_ERROR, sessionData.getLocale()));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CLIENTMAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		case ADMIN:
			request.setAttribute(AttributeConstant.ERROR_RELOG_MESSAGE,
					MessageManager.getProrerty(PropertyConstant.MESSAGE_RE_LOGIN_ERROR, sessionData.getLocale()));
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ADMIN_MAIN);
			router.setPage(page);
			router.setType(RouterType.FORWARD);
			break;
		}
		return router;
	}

}
