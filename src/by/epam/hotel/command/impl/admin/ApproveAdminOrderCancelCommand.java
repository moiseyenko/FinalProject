package by.epam.hotel.command.impl.admin;

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
import by.epam.hotel.logic.ApproveAdminOrderCancelLogic;
import by.epam.hotel.logic.ToAllOrdersLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ApproveAdminOrderCancelCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ApproveAdminOrderCancelCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if(sessionData.getRole() == RoleType.ADMIN) {
			BigDecimal returnedSum = new BigDecimal(request.getParameter("returnedSum"));
			int orderId = Integer.parseInt(request.getParameter("orderId"));
			int accountId = Integer.parseInt(request.getParameter("accountId"));
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ApproveAdminOrderCancelLogic.cancelOrder(accountId, returnedSum, orderId)) {
					List<FullInfoOrder> listAdminFullInfoOrder = ToAllOrdersLogic.getAllFullInfoOrderList(currentPage,
							recordsPerPage);
					sessionData.setListAdminFullInfoOrder(listAdminFullInfoOrder);
					page = ConfigurationManager.getProperty("path.page.allorders");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorAdminOrderCancelMessage", MessageManager.getProrerty("message.adminordercancelerror"));
					page = ConfigurationManager.getProperty("path.page.approveadminordercancel");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		}else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
}
