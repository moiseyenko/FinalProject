package by.epam.hotel.command.impl.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to add or delete customer email to send them letters.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class AddRemoveToSendListCommand implements ActionCommand{

	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} method will add or 
	 * remove specified customer email to(from) send list and return admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page with all customer emails.
	 * Otherwise method will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request){
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
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_EMAILS);
			router.setType(RouterType.REDIRECT);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
}
