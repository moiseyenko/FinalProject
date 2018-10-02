package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ReplenishLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ReplenishCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ReplenishCommand.class);
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.CLIENT) {
			String replenishAmount = request.getParameter("replenishAmount");
			if(validateInputAmount(replenishAmount)) {
				try {
					BigDecimal currentAmount = sessionData.getCurrentAmount();
					BigDecimal bigDecimalReplenishAmount = parseToBigDecimal(replenishAmount);
					currentAmount = currentAmount.add(bigDecimalReplenishAmount);
					try {
						if(ReplenishLogic.updateBankAccount(sessionData.getLogin(), currentAmount)) {
							sessionData.setCurrentAmount(currentAmount);
							page = ConfigurationManager.getProperty("path.page.paypage");
							router.setType(RouterType.REDIRECT);
						}else {
							request.setAttribute("errorReplenishMessage", MessageManager.getProrerty("message.replenisherror"));
							page = ConfigurationManager.getProperty("path.page.replenishpage");
							router.setType(RouterType.FORWARD);
						}
					} catch (ServiceException e) {
						LOG.error(e);
						throw new CommandException(e);
					}
				} catch (ParseException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			}else {
				request.setAttribute("wrongInputReplenishAmount", MessageManager.getProrerty("message.wronginputreplenishamount"));
				page = ConfigurationManager.getProperty("path.page.replenishpage");
				router.setType(RouterType.FORWARD);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;	
	}

	private boolean validateInputAmount(String replenishAmount) {
		String INPUT_PATTERN = "^[0-9]{1,10}.[0-9]{0,2}$";
		Pattern pattern = Pattern.compile(INPUT_PATTERN);
		Matcher matcher = pattern.matcher(replenishAmount);
		return matcher.matches();
	}
	
	private BigDecimal parseToBigDecimal (String number) throws ParseException {
        String processNumber = number.replace(",", ".");
        BigDecimal result = new BigDecimal(processNumber).setScale(2, RoundingMode.HALF_UP);
        return result;
	}

}
