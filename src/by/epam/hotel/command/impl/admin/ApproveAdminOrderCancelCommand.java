package by.epam.hotel.command.impl.admin;

import java.math.BigDecimal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.FullInfoOrder;
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
 * and is used to approve cancel of specified order by admin.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ApproveAdminOrderCancelCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If the order with specified parameters cannot be canceled, method will return admin by 
	 * {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will cancel specified order and return admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with all orders.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if(sessionData.getRole() == RoleType.ADMIN) {
			BigDecimal returnedSum = new BigDecimal(request.getParameter(ParameterConstant.RETURNED_SUM));
			int orderId = Integer.parseInt(request.getParameter(ParameterConstant.ORDER_ID));
			int accountId = Integer.parseInt(request.getParameter(ParameterConstant.ACCOUNT_ID));
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (AdminService.approveCancelOrder(accountId, returnedSum, orderId)) {
					List<FullInfoOrder> listAdminFullInfoOrder = AdminService.getAllFullInfoOrderList(currentPage,
							recordsPerPage);
					sessionData.setListAdminFullInfoOrder(listAdminFullInfoOrder);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_ORDERS);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_ADMIN_ORDER_CANCEL_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_ADMIN_ORDER_CANCEL_ERROR, sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_APPROVE_ADMIN_ORDER_CANCEL);
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		}else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
}
