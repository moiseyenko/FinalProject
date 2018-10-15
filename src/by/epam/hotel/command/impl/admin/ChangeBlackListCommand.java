package by.epam.hotel.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.AdminService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to adding or deletion of specified client to(from) black list.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeBlackListCommand implements ActionCommand {
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If specified client can not be added or removed to(from) black list, method will return user by 
	 * {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will add (if client is not in blacklist) or remove(if client is already in blacklist)
	 * to(from) black list and send admin by{@link by.epam.hotel.util.type.RouterType REDIRECT} to page with 
	 * all clients.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int clientIndex = Integer.parseInt(request.getParameter(ParameterConstant.CLIENT_INDEX));
			Client clientToChangeBlacklist = sessionData.getClientList().get(--clientIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (AdminService.changeClientBlacklist(clientToChangeBlacklist)) {
					List<Client> clientList = AdminService.getClientsList(currentPage,
							recordsPerPage);
					sessionData.setClientList(clientList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_CLIENTS);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_CHANGE_BLACKLIST_CLIENT_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_CLIENT_BLACKLIST_ERROR, sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_CLIENTS);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		return router;
	}

}
