package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ApproveOrderCancelLogic;
import by.epam.hotel.logic.ToAccountOrdersLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class ApproveOrderCancelCommand implements ActionCommand {

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			BigDecimal returnedSum = new BigDecimal(request.getParameter(ParameterConstant.RETURNED_SUM));
			int orderId = Integer.parseInt(request.getParameter(ParameterConstant.ORDER_ID));
			String login = sessionData.getLogin();
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ApproveOrderCancelLogic.cancelOrder(login, returnedSum, orderId)) {
					List<FullInfoOrder> listFullInfoOrder = ToAccountOrdersLogic.getFullInfoOrderList(login, currentPage, recordsPerPage);
					sessionData.setListAccountFullInfoOrder(listFullInfoOrder);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ACCOUNT_ORDERS);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_ORDER_CANCEL_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_ORDER_CANCEL_ERROR));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_APPROVE_ORDER_CANCEL);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}

}
