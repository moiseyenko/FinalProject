package by.epam.hotel.command;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.entity.Router;
import by.epam.hotel.exception.CommandException;

/**
 * The basic interface for all action commands. This interface defines one of the many actions performed by the application.
 * 
 * @author User
 *
 */
public interface ActionCommand {
	
	/**
	 * Execute action and  return {@link Router} instance with path page 
	 * and {@link by.epam.hotel.util.type.RouterType RouterType}
	 * 
	 * @param request provides data to execute method 
	 * @return {@link by.epam.hotel.entity.Router Router} instance 
	 * with {@link java.lang.String String} path page 
	 * and {@link by.epam.hotel.util.type.RouterType RouterType}
	 * @throws CommandException if method has catched {@link by.epam.hotel.exception.ServiceException ServiceException}
	 */
	Router execute(HttpServletRequest request) throws CommandException;
}
