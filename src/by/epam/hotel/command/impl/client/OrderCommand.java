package by.epam.hotel.command.impl.client;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.service.CommonService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to send client to order page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class OrderCommand implements ActionCommand {
	
	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#CLIENT CLIENT}
	 * method will return client by {@link by.epam.hotel.util.type.RouterType
	 * FORWARD} to  order page. Otherwise method will return
	 * user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		SessionData sessionData = (SessionData) request.getSession().getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			try {
				sessionData.setClients(ClientService.getClientList(sessionData.getLogin()));
				sessionData.setNationalities(ClientService.getNationalityList());
				sessionData.setRoomClasses(CommonService.getRoomClassList());
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ORDER);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		router.setType(RouterType.FORWARD);
		return router;
	}

}
