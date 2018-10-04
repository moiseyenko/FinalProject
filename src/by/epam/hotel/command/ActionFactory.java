package by.epam.hotel.command;

public class ActionFactory {

	public ActionCommand defineCommand(String action) {
		CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
		ActionCommand current = currentEnum.getCurrentCommand();
		return current;
	}
}
