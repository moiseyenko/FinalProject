package by.epam.hotel.command.impl.admin;

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
import by.epam.hotel.dao.entity.RoomClass;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.CreateClassLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class CreateClassCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(CreateClassCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			String classId = request.getParameter("classId");
			if(validateClassId(classId)) {
				RoomClass newRoomClass = new RoomClass(classId);
				try {
					if(CreateClassLogic.createRoomClass(newRoomClass)) {
						page = ConfigurationManager.getProperty("path.page.successcreateclass");
						router.setType(RouterType.REDIRECT);
					}else {
						request.setAttribute("errorCreateClassMessage", MessageManager.getProrerty("message.createclasserror"));
						page = ConfigurationManager.getProperty("path.page.createclass");
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					LOG.error(e);
					throw new CommandException(e);
				}
			}else {
				request.setAttribute("errorClassIdMessage", MessageManager.getProrerty("message.classiderror"));
				page = ConfigurationManager.getProperty("path.page.createclass");
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateClassId(String classId) {
		String LOGIN_PATTERN = "^[A-Za-zÀ-ÿà-ÿ0-9 ¨¸'-]{1,25}$";
		Pattern pattern = Pattern.compile(LOGIN_PATTERN);
		Matcher matcher = pattern.matcher(classId);
		return matcher.matches();
	}
	
}
