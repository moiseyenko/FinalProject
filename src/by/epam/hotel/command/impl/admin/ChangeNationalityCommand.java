package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.RoleType;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.RouterType;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.dao.entity.Nationality;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;

public class ChangeNationalityCommand implements ActionCommand {
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute("sessionData");
		if (sessionData.getRole() == RoleType.ADMIN) {
			int nationalityIndex = Integer.parseInt(request.getParameter("nationalityIndex"));
			Nationality nationalityToChange = sessionData.getNationalityList().get(--nationalityIndex);
			sessionData.setNationalityToChange(nationalityToChange);
			page = ConfigurationManager.getProperty("path.page.changenationality");
		} else {
			page = ConfigurationManager.getProperty("path.page.welcome");
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
