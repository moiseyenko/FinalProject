package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to send specified order to page confirming its cancellation.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class CancelOrderAdminCommand implements ActionCommand {
	
	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} method will
	 * send specified order to page confirming its cancellation.
	 * Otherwise method will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int orderIndex = Integer.parseInt(request.getParameter(ParameterConstant.ORDER_INDEX));
			FullInfoOrder orderToCancel = sessionData.getListAdminFullInfoOrder().get(--orderIndex);
			request.setAttribute(AttributeConstant.ORDER_TO_CANCEL, orderToCancel);
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_APPROVE_ADMIN_ORDER_CANCEL);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
