package by.epam.hotel.command;

import javax.servlet.http.HttpServletRequest;

import by.epam.hotel.controller.Router;
import by.epam.hotel.exception.CommandException;

public interface ActionCommand {
	Router execute(HttpServletRequest request) throws CommandException;
}
