package by.epam.hotel.command.impl.admin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a {@link by.epam.hotel.command.ActionCommand ActionCommand} interface 
 * and is used to setting of a specific nationality to change.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ChangeNationalityCommand implements ActionCommand {
	
	/**
	 * If user's role equals to {@link by.epam.hotel.util.type.RoleType#ADMIN ADMIN}, method will set
	 * specified nationality to change and will send admin by
	 * {@link by.epam.hotel.util.type.RouterType REDIRECT} to page of nationality changes.
	 * Otherwise method  returns user by {@link by.epam.hotel.util.type.RouterType FORWARD} to welcome page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int nationalityIndex = Integer.parseInt(request.getParameter(ParameterConstant.NATIONALITY_INDEX));
			Nationality nationalityToChange = sessionData.getNationalityList().get(--nationalityIndex);
			sessionData.setNationalityToChange(nationalityToChange);
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_CHANGE_NATIONALITY);
		} else {
			page = ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME);
		}
		router.setType(RouterType.FORWARD);
		router.setPage(page);
		return router;
	}

}
