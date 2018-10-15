package by.epam.hotel.command;
/**
 * Class {@code ActionFactory} is used to define the commnad type and create a command instance.
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public class ActionFactory {
	/**
	 * Based on the received value of the action command retrieves the corresponding 
	 * command object
	 * 
	 * 
	 * @param action 
	 * 		  the command type
	 * 
	 * @return the implementation of {@link ActionCommand} 
	 */
	public static ActionCommand defineCommand(String action) {
		CommandType currentEnum = CommandType.valueOf(action.toUpperCase());
		ActionCommand current = currentEnum.getCurrentCommand();
		return current;
	}
}
