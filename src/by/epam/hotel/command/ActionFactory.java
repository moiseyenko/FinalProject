package by.epam.hotel.command;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.command.impl.EmptyCommand;
import by.epam.hotel.util.MessageManager;

public class ActionFactory {

	public ActionCommand defineCommand(HttpServletRequest requset) {
		ActionCommand current = new EmptyCommand();
		String action = requset.getParameter("command");
		if (action == null || action.isEmpty()) {
			return current;
		}
		// TODO remove try catch
		try {
			CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
			current = currentEnum.getCurrentCommand();
		} catch (IllegalArgumentException e) {
			requset.setAttribute("wrongAction", action + MessageManager.getProrerty("message.wrongaction"));
		}
		return current;
	}
}
