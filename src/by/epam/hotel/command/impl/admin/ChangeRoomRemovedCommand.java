package by.epam.hotel.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.Room;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ChangeRoomRemovedLogic;
import by.epam.hotel.logic.ToAllRoomsLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;

public class ChangeRoomRemovedCommand implements ActionCommand{
	private static final Logger LOG = LogManager.getLogger(ChangeRoomRemovedCommand.class);
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int roomIndex = Integer.parseInt(request.getParameter("roomIndex"));
			Room roomToChangeRemoved = sessionData.getRoomList().get(--roomIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ChangeRoomRemovedLogic.changeRoomRemoved(roomToChangeRemoved)) {
					List<Room> roomList = ToAllRoomsLogic.getRoomsList(currentPage,
							recordsPerPage);
					sessionData.setRoomList(roomList);
					page = ConfigurationManager.getProperty("path.page.allrooms");
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute("errorChangeRoomRemovedMessage", MessageManager.getProrerty("message.changeroomremovederror"));
					page = ConfigurationManager.getProperty("path.page.allrooms");
					router.setType(RouterType.FORWARD);
				}
			} catch (ServiceException e) {
				LOG.error(e);
				throw new CommandException(e);
			}
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setPage(page);
		return router;
	}

}
