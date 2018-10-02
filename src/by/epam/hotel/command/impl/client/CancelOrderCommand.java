package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

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

public class CancelOrderCommand implements ActionCommand {
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			int orderIndex = Integer.parseInt(request.getParameter("orderIndex")) ;
			List<FullInfoOrder> ordersList = sessionData.getListAccountFullInfoOrder();
			FullInfoOrder orderToCancel = ordersList.get(--orderIndex);
			BigDecimal returnedSum = calculateReturnSum(orderToCancel);
			request.setAttribute("returnedSum", returnedSum);
			request.setAttribute("orderToCancel", orderToCancel);
			page = ConfigurationManager.getProperty("path.page.approveordercancel");
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
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
