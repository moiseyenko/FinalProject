package by.epam.hotel.command.impl.admin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.CreateClassLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.constant.ValidationConstant;

public class CreateClassCommand implements ActionCommand{
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			String classId = request.getParameter(ParameterConstant.CLASS_ID);
			if(validateClassId(classId)) {
				RoomClass newRoomClass = new RoomClass(classId);
				try {
					if(CreateClassLogic.createRoomClass(newRoomClass)) {
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_SUCCESS_CREATE_CLASS);
						router.setType(RouterType.REDIRECT);
					}else {
						request.setAttribute(AttributeConstant.ERROR_CREATE_CLASS_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_CREATE_CLASS_ERROR));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_CLASS);
						router.setType(RouterType.FORWARD);
					}
				} catch (ServiceException e) {
					throw new CommandException(e);
				}
			}else {
				request.setAttribute(AttributeConstant.ERROR_CLASS_ID_MESSAGE,
						MessageManager.getProrerty(PropertyConstant.MESSAGE_CLASS_ID_ERROR));
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CREATE_CLASS);
				router.setType(RouterType.FORWARD);
			}	
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
			router.setType(RouterType.FORWARD);
		}
		router.setPage(page);
		return router;
	}
	
	private boolean validateClassId(String classId) {
		Pattern pattern = Pattern.compile(ValidationConstant.CLASS_ID_PATTERN);
		Matcher matcher = pattern.matcher(classId);
		return matcher.matches();
	}
	
}
