package by.epam.hotel.command.impl.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class AddRemoveToSendListCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int accountIndex = Integer.parseInt(request.getParameter(ParameterConstant.ACCOUNT_INDEX));
			Account markedAccount = sessionData.getAccountList().get(--accountIndex);
			Set<Account> sendList = sessionData.getSendList();
			if(sendList.contains(markedAccount)) {
				sendList.remove(markedAccount);
			}else {
				sendList.add(markedAccount);
			}
			page = ConfigurationManager.getProperty("path.page.allemails");
			router.setType(RouterType.REDIRECT);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		return router;
	}
	
}
