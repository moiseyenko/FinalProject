package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ReplenishLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class ReplenishCommand implements ActionCommand{
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			String replenishAmount = request.getParameter(ParameterConstant.REPLENISH_AMOUNT);
			if(validateInputAmount(replenishAmount)) {
				try {
					BigDecimal currentAmount = sessionData.getCurrentAmount();
					BigDecimal bigDecimalReplenishAmount = parseToBigDecimal(replenishAmount);
					currentAmount = currentAmount.add(bigDecimalReplenishAmount);
					try {
						if(ReplenishLogic.updateBankAccount(sessionData.getLogin(), currentAmount)) {
							sessionData.setCurrentAmount(currentAmount);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute(AttributeConstant.ERROR_REPLENISH_MESSAGE,
									MessageManager.getProrerty(PropertyConstant.MESSAGE_REPLENISH_ERROR));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_REPLENISH_PAGE);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					throw new CommandException(e);
				}
			}else {
				request.setAttribute(AttributeConstant.ERROR_INPUT_WRONG_REPLENISH_AMOUNT,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_INPUT_REPLENISH_AMOUNT_ERROR));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_REPLENISH_PAGE);
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;	
	}

	private boolean validateInputAmount(String replenishAmount) {
		Pattern pattern = Pattern.compile(ValidationConstant.PRICE_PATTERN);
		Matcher matcher = pattern.matcher(replenishAmount);
		return matcher.matches();
	}
	
	private BigDecimal parseToBigDecimal (String number) throws ParseException {
        String processNumber = number.replace(ValidationConstant.COMMA, ValidationConstant.DOT);
        BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
        return result;
	}

}
