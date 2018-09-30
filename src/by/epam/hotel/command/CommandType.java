package by.epam.hotel.command;

import by.epam.hotel.command.impl.ChangeLocaleCommand;
import by.epam.hotel.command.impl.ChangeLoginCommand;
import by.epam.hotel.command.impl.ChangePasswordCommand;
import by.epam.hotel.command.impl.ToChangePersonalDataCommand;
import by.epam.hotel.command.impl.ToDeleteAccountCommand;
import by.epam.hotel.command.impl.ChooseRoomCommand;
import by.epam.hotel.command.impl.DeleteAccountCommand;
import by.epam.hotel.command.impl.FillOrderFormCommand;
import by.epam.hotel.command.impl.FindRoomCommand;
import by.epam.hotel.command.impl.LoginBackCommand;
import by.epam.hotel.command.impl.LoginCommand;
import by.epam.hotel.command.impl.LogoutCommand;
import by.epam.hotel.command.impl.ApproveOrderCancelCommand;
import by.epam.hotel.command.impl.BackToAccountOrdersCommand;
import by.epam.hotel.command.impl.BackToChangePersonalDataCommand;
import by.epam.hotel.command.impl.BackToClientmainCommand;
import by.epam.hotel.command.impl.BackToInfoPaymentCommnamd;
import by.epam.hotel.command.impl.BackToOrderCommand;
import by.epam.hotel.command.impl.BackToPayPageCommand;
import by.epam.hotel.command.impl.BackToRoomsCommand;
import by.epam.hotel.command.impl.CancelOrderCommand;
import by.epam.hotel.command.impl.OrderCommand;
import by.epam.hotel.command.impl.PayCommand;
import by.epam.hotel.command.impl.ReplenishCommand;
import by.epam.hotel.command.impl.ToReplenishCommand;
import by.epam.hotel.command.impl.SignUpCommand;
import by.epam.hotel.command.impl.SignupBackCommand;
import by.epam.hotel.command.impl.ToAccountOrdersCommand;
import by.epam.hotel.command.impl.ToChangeLoginCommand;
import by.epam.hotel.command.impl.ToLoginCommand;
import by.epam.hotel.command.impl.ToPayCommand;
import by.epam.hotel.command.impl.ToSighUpCommand;
import by.epam.hotel.command.impl.ToChangePasswordCommand;

public enum CommandType {
	LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand()), SIGNUP(new SignUpCommand()),
	ORDER(new OrderCommand()), TOLOGIN(new ToLoginCommand()), TOSIGNUP(new ToSighUpCommand()),
	BACKTOCLIENTMAIN (new BackToClientmainCommand()), LOGINBACK(new LoginBackCommand()), SIGNUPBACK(new SignupBackCommand()),
	CHANGELOCALE(new ChangeLocaleCommand()), FILLORDERFORM(new FillOrderFormCommand()), FINDROOM(new FindRoomCommand()),
	BACKTOORDER (new BackToOrderCommand()), CHOOSEROOM(new ChooseRoomCommand()), BACKTOROOMS(new BackToRoomsCommand()),
	TOPAY(new ToPayCommand()), BACKTOINFOPAYMENT(new BackToInfoPaymentCommnamd()), TOREPLENISH(new ToReplenishCommand()),
	BACKTOPAYPAGE(new BackToPayPageCommand()), REPLENISH(new ReplenishCommand()), PAY(new PayCommand()), 
	TOACCOUNTORDERS(new ToAccountOrdersCommand()), CANCELORDER(new CancelOrderCommand()), BACKTOACCOUNTORDERS(new BackToAccountOrdersCommand()),
	APPROVEORDERCANCEL(new ApproveOrderCancelCommand()), TOCHANGEPERSONALDATA(new ToChangePersonalDataCommand()),
	TOCHANGELOGIN(new ToChangeLoginCommand()),TOCHANGEPASSWORD(new ToChangePasswordCommand()), TODELETEACCOUNT(new ToDeleteAccountCommand()),
	CHANGELOGIN(new ChangeLoginCommand()), BACKTOCHANGEPERSONALDATA(new BackToChangePersonalDataCommand()), 
	CHANGEPASSWORD(new ChangePasswordCommand()), DELETEACCOUNT(new DeleteAccountCommand());
	
	private CommandType(ActionCommand command) {
		this.command = command;
	}

	private ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
