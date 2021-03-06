package by.epam.hotel.command.impl.client;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.Router;
import by.epam.hotel.entity.SessionData;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;
import by.epam.hotel.util.type.RouterType;

/**
 * This class is an implementation of a
 * {@link by.epam.hotel.command.ActionCommand ActionCommand} interface and is
 * used to provide transition to payment page.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public class ToPayCommand implements ActionCommand {

	/**
	 * If user's role does not equal to
	 * {@link by.epam.hotel.util.type.RoleType#CLIENT CLIENT} method will return
	 * user by {@link by.epam.hotel.util.type.RouterType#FORWARD FORWARD} to welcome
	 * page. If selected hotel room has already been ordered, method will return
	 * back client by {@link by.epam.hotel.util.type.RouterType#FORWARD FORWARD} to
	 * page with according information.If client does not have bank account, method
	 * will return back client by {@link by.epam.hotel.util.type.RouterType#FORWARD
	 * FORWARD} to previous page. Otherwise method will send client to payment page.
	 */
	@Override
	public Router execute(HttpServletRequest request) throws CommandException {
		Router router = new Router();
		String page = null;
		HttpSession session = request.getSession();
		SessionData sessionData = (SessionData) session.getAttribute(AttributeConstant.SESSION_DATA);
		if (sessionData.getRole() == RoleType.CLIENT) {
			Room chosenRoom = sessionData.getChosenRoom();
			LocalDate from = sessionData.getFrom();
			LocalDate to = sessionData.getTo();
			try {
				List<Room> updatedAvailableRoomList = ClientService.findAvailableRoom(chosenRoom.getCapacity(),
						chosenRoom.getClassRoom(), from, to);
				if (updatedAvailableRoomList.contains(chosenRoom)) {
					BigDecimal currentAmount = ClientService.getCurrentAmount(sessionData.getLogin());
					if (currentAmount != null) {
						sessionData.setCurrentAmount(currentAmount);
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_PAYPAGE);
					} else {
						request.setAttribute(AttributeConstant.ERROR_FIND_BANK_ACCOUNT_MESSAGE,
								MessageManager.getProrerty(PropertyConstant.MESSAGE_FIND_IN_BANK_ACCOUNT_ERROR,
										sessionData.getLocale()));
						page = ConfigurationManager.getProperty(PropertyConstant.PAGE_INFO_FOR_PAYMENT);
						router.setType(RouterType.FORWARD);
					}
				} else {
					sessionData.setAvailableRoomList(updatedAvailableRoomList);
					page = ConfigurationManager.getProperty(PropertyConstant.PAGE_ALREADY_ORDERED_ROOM);
				}
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
