package by.epam.hotel.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
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

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to remove or restore specified room class.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeClassRemovedCommand implements ActionCommand{
	
	/**
	 * If user's role does not equal to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN} 
	 * method  will return user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 * If specified room class can not be removed or restored, method will return user by 
	 * {@link by.epam.hotel.util.type.RouterType FORWARD} to previous page.
	 * Otherwise method will restore (if room class was removed early) or remove specified room class
	 * and send admin by{@link by.epam.hotel.util.type.RouterType REDIRECT} to page with 
	 * all rooms' classes.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int classIndex = Integer.parseInt(request.getParameter(ParameterConstant.CLASS_INDEX));
			RoomClass classToChangeRemoved = sessionData.getRoomClassList().get(--classIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (AdminService.changeClassRemoved(classToChangeRemoved)) {
					List<RoomClass> roomClassList = AdminService.getClassesList(currentPage,
							recordsPerPage);
					sessionData.setRoomClassList(roomClassList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_CLASSES);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_CHANGE_CLASS_REMOVED_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_CLASS_REMOVED_ERROR, sessionData.getLocale()));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_CLASSES);
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
