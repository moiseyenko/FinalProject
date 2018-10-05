package by.epam.hotel.command.impl.admin;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Account;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.SendMessageLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class SendMessageCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String subject = request.getParameter("subject");
			String text = request.getParameter("text");
			Set<Account> sendList = sessionData.getSendList();
			try {
				if (SendMessageLogic.sendMessage(sendList, subject, text)) {
					sessionData.setSendList(null);
					page = ConfigurationManager.getProperty("path.page.successsendmessage");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorSendMessageMessage",
							MessageManager.getProrerty("message.sendmessageerror"));
					page = ConfigurationManager.getProperty("path.page.addsubjectandtextandsend");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setPage(page);
		return router;
	}

}
