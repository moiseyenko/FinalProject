package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;
import by.epam.hotel.util.validator.RoomValidator;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to replenish client's bank account.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ReplenishCommand implements ActionCommand {

	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#CLIENT
	 * CLIENT} method will return user by {@link by.epam.hotel.util.type.RouterType
	 * FORWARD} to welcome page. If inputted replenish amount is incorrect or if
	 * client's bank account can not be updated with replenish amount, method will
	 * return back client to replenish page with according information. Otherwise
	 * method will provide replenishment of client's bank account and send client by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to order payment page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			String replenishAmount = request.getParameter(ParameterConstant.REPLENISH_AMOUNT);
			if (RoomValidator.validateCurrency(replenishAmount)) {
				try {
					BigDecimal currentAmount = sessionData.getCurrentAmount();
					BigDecimal bigDecimalReplenishAmount = parseToBigDecimal(replenishAmount);
					currentAmount = currentAmount.add(bigDecimalReplenishAmount);
					try {
						if (ClientService.updateBankAccount(sessionData.getLogin(), currentAmount)) {
							sessionData.setCurrentAmount(currentAmount);
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
							router.setType(RouterType.REDIRECT);
						} else {
							request.setAttribute(AttributeConstant.ERROR_REPLENISH_MESSAGE, MessageManager
									.getProrerty(PropertyConstant.MESSAGE_REPLENISH_ERROR, sessionData.getLocale()));
							page = ConfigurationManager.getProperty(PropertyConstant.PAGE_REPLENISH_PAGE);
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					throw new CommandException(e);
				}
			} else {
				request.setAttribute(AttributeConstant.ERROR_INPUT_WRONG_REPLENISH_AMOUNT, MessageManager
						.getProrerty(PropertyConstant.MESSAGE_INPUT_REPLENISH_AMOUNT_ERROR, sessionData.getLocale()));
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

	private BigDecimal parseToBigDecimal(String number) throws ParseException {
		String processNumber = number.replace(ValidationConstant.COMMA, ValidationConstant.DOT);
		BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
		return result;
	}

}
