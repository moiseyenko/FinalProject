package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.CommonService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to setting of a specific room to change.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeRoomCommand implements ActionCommand{
	
	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will set
	 * specified room to change and will send admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page of room changes.
	 * Otherwise method  returns user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int roomIndex = Integer.parseInt(request.getParameter(ParameterConstant.ROOM_INDEX));
			Room roomToChange = sessionData.getRoomList().get(--roomIndex);
			try {
				sessionData.setRoomClasses(CommonService.getRoomClassList());
				sessionData.setRoomToChange(roomToChange);
				page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_ROOM);
			} catch (ServiceException e) {
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
