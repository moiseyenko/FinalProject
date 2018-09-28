package by.epam.hotel.command;

import by.epam.hotel.command.impl.ChangeLocaleCommand;
import by.epam.hotel.command.impl.ChooseRoomCommand;
import by.epam.hotel.command.impl.FillOrderFormCommand;
import by.epam.hotel.command.impl.FindRoomCommand;
import by.epam.hotel.command.impl.LoginBackCommand;
import by.epam.hotel.command.impl.LoginCommand;
import by.epam.hotel.command.impl.LogoutCommand;
import by.epam.hotel.command.impl.BackToClientmainCommand;
import by.epam.hotel.command.impl.BackToOrderCommand;
import by.epam.hotel.command.impl.BackToRoomsCommand;
import by.epam.hotel.command.impl.OrderCommand;
import by.epam.hotel.command.impl.SignUpCommand;
import by.epam.hotel.command.impl.SignupBackCommand;
import by.epam.hotel.command.impl.ToLoginCommand;
import by.epam.hotel.command.impl.ToPayCommand;
import by.epam.hotel.command.impl.ToSighUpCommand;

public enum CommandType {
	LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand()), SIGNUP(new SignUpCommand()),
	ORDER(new OrderCommand()), TOLOGIN(new ToLoginCommand()), TOSIGNUP(new ToSighUpCommand()),
	BACKTOCLIENTMAIN (new BackToClientmainCommand()), LOGINBACK(new LoginBackCommand()), SIGNUPBACK(new SignupBackCommand()),
	CHANGELOCALE(new ChangeLocaleCommand()), FILLORDERFORM(new FillOrderFormCommand()), FINDROOM(new FindRoomCommand()),
	BACKTOORDER (new BackToOrderCommand()), CHOOSEROOM(new ChooseRoomCommand()), BACKTOROOMS(new BackToRoomsCommand()),
	TOPAY(new ToPayCommand());
	private CommandType(ActionCommand command) {
		this.command = command;
	}

	private ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
