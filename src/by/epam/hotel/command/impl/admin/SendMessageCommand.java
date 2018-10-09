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
import by.epam.hotel.service.AdminService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

public class SendMessageCommand implements ActionCommand{

	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String subject = request.getParameter(ParameterConstant.SUBJECT);
			String text = request.getParameter("text");
			Set<Account> sendList = sessionData.getSendList();
			try {
				if (AdminService.sendMessage(sendList, subject, text)) {
					sessionData.setSendList(null);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_SEND_MSG);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_SEND_MSG_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_SEND_MSG_ERROR,
									sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUBJECT_TEXT_SEND);
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
