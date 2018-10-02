package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.FullInfoOrder;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;

public class CancelOrderAdminCommand implements ActionCommand {
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int orderIndex = Integer.parseInt(request.getParameter("orderIndex"));
			FullInfoOrder orderToCancel = sessionData.getListAdminFullInfoOrder().get(--orderIndex);
			request.setAttribute("orderToCancel", orderToCancel);
			page = ConfigurationManager.getProperty("path.page.approveadminordercancel");
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
