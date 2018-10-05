package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoField;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class CancelOrderCommand implements ActionCommand {
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			int orderIndex = Integer.parseInt(request.getParameter(ParameterConstant.ORDER_ID)) ;
			FullInfoOrder orderToCancel =sessionData.getListAccountFullInfoOrder().get(--orderIndex);
			BigDecimal returnedSum = calculateReturnSum(orderToCancel);
			request.setAttribute(AttributeConstant.RETURNED_SUM, returnedSum);
			request.setAttribute(AttributeConstant.ORDER_TO_CANCEL, orderToCancel);
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_APPROVE_ORDER_CANCEL);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;

	}

	private BigDecimal calculateReturnSum(FullInfoOrder orderForCancel) {
		LocalDate from = orderForCancel.getFrom();
		LocalDate now = LocalDate.now();
		long timeStampDiff = from.getLong(ChronoField.EPOCH_DAY)-now.getLong(ChronoField.EPOCH_DAY);
		double rate;
		if(timeStampDiff>=7) {
			rate = 1;
		}else if(timeStampDiff>=2) {
			rate = 0.8;
		}else if(timeStampDiff==1) {
			rate = 0.5;
		}else {
			rate = 0.1;
		}
		BigDecimal orderCost = orderForCancel.getCost();		
		double doubleReturnedSum = orderCost.doubleValue()*rate;
		BigDecimal result = new BigDecimal(doubleReturnedSum).setScale(2, RoundingMode.HALF_UP);
		return result;
	}

	

}
