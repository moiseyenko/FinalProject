package by.epam.hotel.command.impl.admin;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.controller.Router;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.logic.ChangeNationalityRemovedLogic;
import by.epam.hotel.logic.ToAllNationalitiesLogic;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.apptype.RoleType;
import by.epam.hotel.util.apptype.RouterType;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class ChangeNationalityRemovedCommand implements ActionCommand{
	
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.ADMIN) {
			int nationalityIndex = Integer.parseInt(request.getParameter(ParameterConstant.NATIONALITY_INDEX));
			Nationality nationalityToChangeRemoved = sessionData.getNationalityList().get(--nationalityIndex);
			int recordsPerPage = sessionData.getRecordsPerPage();
			int currentPage = sessionData.getCurrentPage();
			try {
				if (ChangeNationalityRemovedLogic.changeNationalityRemoved(nationalityToChangeRemoved)) {
					List<Nationality> nationalityList = ToAllNationalitiesLogic.getNationalitiesList(currentPage,
							recordsPerPage);
					sessionData.setNationalityList(nationalityList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_NATIONALITIES);
					router.setType(RouterType.REDIRECT);
				}else {
					request.setAttribute(AttributeConstant.ERROR_CHANGE_NATIONALITY_REMOVED_MESSAGE,
							MessageManager.getProrerty(PropertyConstant.MESSAGE_CHANGE_NATIONALITY_REMOVED_ERROR));
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALL_NATIONALITIES);
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
