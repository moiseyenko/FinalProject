package by.epam.hotel.command.impl;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.FullInfoOrder;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ApproveOrderLogic;
import by.epam.hotel.logic.ToAccountOrdersLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ApproveOrderCancelCommand implements ActionCommand {
	private static final Logger LOG = LogManager.getLogger(ApproveOrderCancelCommand.class);

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			BigDecimal returnedSum = new BigDecimal(request.getParameter("returnedSum"));
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			String login = sessionData.getLogin();
			try {
				if (ApproveOrderLogic.cancelOrder(login, returnedSum, orderId)) {
					List<FullInfoOrder> listFullInfoOrder = ToAccountOrdersLogic.getFullInfoOrderList(login);
					sessionData.setListFullInfoOrder(listFullInfoOrder);
					page = ConfigurationManager.getProperty("path.page.accountorders");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorOrderCancelMessage", MessageManager.getProrerty("message.ordercancelerror"));
					page = ConfigurationManager.getProperty("path.page.approveordercancel");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}

		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
