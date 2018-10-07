package by.epam.hotel.command;

import by.epam.hotel.command.impl.BackToSignupCommand;
import by.epam.hotel.command.impl.ChangeLocaleCommand;
import by.epam.hotel.command.impl.ChangeLoginCommand;
import by.epam.hotel.command.impl.ChangePasswordCommand;
import by.epam.hotel.command.impl.CheckKeyAndSignUpCommand;
import by.epam.hotel.command.impl.ToChangePersonalDataCommand;
import by.epam.hotel.command.impl.ToDeleteAccountCommand;
import by.epam.hotel.command.impl.DeleteAccountCommand;
import by.epam.hotel.command.impl.LoginBackCommand;
import by.epam.hotel.command.impl.LoginCommand;
import by.epam.hotel.command.impl.LogoutCommand;
import by.epam.hotel.command.impl.SignUpCommand;
import by.epam.hotel.command.impl.SignupBackCommand;
import by.epam.hotel.command.impl.ToChangeLoginCommand;
import by.epam.hotel.command.impl.ToLoginCommand;
import by.epam.hotel.command.impl.ToSighUpCommand;
import by.epam.hotel.command.impl.admin.AddRemoveToSendListCommand;
import by.epam.hotel.command.impl.admin.ApproveAdminOrderCancelCommand;
import by.epam.hotel.command.impl.admin.ApproveChangeNationalityCommand;
import by.epam.hotel.command.impl.admin.ApproveChangeRoomCommand;
import by.epam.hotel.command.impl.admin.BackToAdminClassesCommand;
import by.epam.hotel.command.impl.admin.BackToAdminNationalitiesCommand;
import by.epam.hotel.command.impl.admin.BackToAdminRoomsCommand;
import by.epam.hotel.command.impl.admin.BackToAdminmainCommand;
import by.epam.hotel.command.impl.admin.BackToAllEmailsCommand;
import by.epam.hotel.command.impl.admin.BackToAllNationalitiesCommand;
import by.epam.hotel.command.impl.admin.BackToAllOrdersCommand;
import by.epam.hotel.command.impl.admin.BackToAllRoomsCommand;
import by.epam.hotel.command.impl.admin.CancelOrderAdminCommand;
import by.epam.hotel.command.impl.admin.ChangeAdminRightsCommand;
import by.epam.hotel.command.impl.admin.ChangeBlackListCommand;
import by.epam.hotel.command.impl.admin.ChangeClassRemovedCommand;
import by.epam.hotel.command.impl.admin.ChangeNationalityCommand;
import by.epam.hotel.command.impl.admin.ChangeNationalityRemovedCommand;
import by.epam.hotel.command.impl.admin.ChangeRoomCommand;
import by.epam.hotel.command.impl.admin.ChangeRoomRemovedCommand;
import by.epam.hotel.command.impl.admin.CreateClassCommand;
import by.epam.hotel.command.impl.admin.CreateNationalityCommand;
import by.epam.hotel.command.impl.admin.CreateRoomCommand;
import by.epam.hotel.command.impl.admin.SendMessageCommand;
import by.epam.hotel.command.impl.admin.ToSubjectTextSendCommand;
import by.epam.hotel.command.impl.admin.ToAdminClassesCommand;
import by.epam.hotel.command.impl.admin.ToAdminNationalitiesCommand;
import by.epam.hotel.command.impl.admin.ToAllClientsCommand;
import by.epam.hotel.command.impl.admin.ToAllEmailsCommand;
import by.epam.hotel.command.impl.admin.ToAllNationalitiesCommand;
import by.epam.hotel.command.impl.admin.ToAllOrdersCommand;
import by.epam.hotel.command.impl.admin.ToAllRoomsCommand;
import by.epam.hotel.command.impl.admin.ToCreateClassCommand;
import by.epam.hotel.command.impl.admin.ToCreateNationalityCommand;
import by.epam.hotel.command.impl.admin.ToCreateRoomCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageAccountCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageClassCommand;
import by.epam.hotel.command.impl.admin.ToAdminRoomsCommand;
import by.epam.hotel.command.impl.admin.ToAllAccountsCommand;
import by.epam.hotel.command.impl.admin.ToAllClassesCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageClientCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageEmailCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageOrderCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageRoomCommand;
import by.epam.hotel.command.impl.admin.ToSetRecordsPerPageNationalityCommand;
import by.epam.hotel.command.impl.client.ApproveOrderCancelCommand;
import by.epam.hotel.command.impl.client.BackToAccountOrdersCommand;
import by.epam.hotel.command.impl.client.BackToChangePersonalDataCommand;
import by.epam.hotel.command.impl.client.BackToClientmainCommand;
import by.epam.hotel.command.impl.client.BackToInfoPaymentCommnamd;
import by.epam.hotel.command.impl.client.BackToOrderCommand;
import by.epam.hotel.command.impl.client.BackToPayPageCommand;
import by.epam.hotel.command.impl.client.BackToRoomsCommand;
import by.epam.hotel.command.impl.client.CancelOrderCommand;
import by.epam.hotel.command.impl.client.ChooseRoomCommand;
import by.epam.hotel.command.impl.client.FillOrderFormCommand;
import by.epam.hotel.command.impl.client.FindRoomCommand;
import by.epam.hotel.command.impl.client.OrderCommand;
import by.epam.hotel.command.impl.client.PayCommand;
import by.epam.hotel.command.impl.client.ReplenishCommand;
import by.epam.hotel.command.impl.client.ToAccountOrdersCommand;
import by.epam.hotel.command.impl.client.ToPayCommand;
import by.epam.hotel.command.impl.client.ToReplenishCommand;
import by.epam.hotel.command.impl.client.ToSetRecordsPerPageAccountOrderCommand;
import by.epam.hotel.command.impl.ToChangePasswordCommand;

public enum CommandType {
	LOGIN(new LoginCommand()), LOGOUT(new LogoutCommand()), SIGNUP(new SignUpCommand()),
	ORDER(new OrderCommand()), TOLOGIN(new ToLoginCommand()), TOSIGNUP(new ToSighUpCommand()),
	BACKTOCLIENTMAIN (new BackToClientmainCommand()), LOGINBACK(new LoginBackCommand()), SIGNUPBACK(new SignupBackCommand()),
	CHANGELOCALE(new ChangeLocaleCommand()), FILLORDERFORM(new FillOrderFormCommand()), FINDROOM(new FindRoomCommand()),
	BACKTOORDER (new BackToOrderCommand()), CHOOSEROOM(new ChooseRoomCommand()), BACKTOROOMS(new BackToRoomsCommand()),
	TOPAY(new ToPayCommand()), BACKTOINFOPAYMENT(new BackToInfoPaymentCommnamd()), TOREPLENISH(new ToReplenishCommand()),
	BACKTOPAYPAGE(new BackToPayPageCommand()), REPLENISH(new ReplenishCommand()), PAY(new PayCommand()),
	TOSETRECORDSPERPAGEACCOUNTORDER(new ToSetRecordsPerPageAccountOrderCommand()), 
	TOACCOUNTORDERS(new ToAccountOrdersCommand()), CANCELORDER(new CancelOrderCommand()), BACKTOACCOUNTORDERS(new BackToAccountOrdersCommand()),
	APPROVEORDERCANCEL(new ApproveOrderCancelCommand()), 
	TOCHANGEPERSONALDATA(new ToChangePersonalDataCommand()),
	TOCHANGELOGIN(new ToChangeLoginCommand()),TOCHANGEPASSWORD(new ToChangePasswordCommand()), TODELETEACCOUNT(new ToDeleteAccountCommand()),
	CHANGELOGIN(new ChangeLoginCommand()), BACKTOCHANGEPERSONALDATA(new BackToChangePersonalDataCommand()), 
	CHANGEPASSWORD(new ChangePasswordCommand()), DELETEACCOUNT(new DeleteAccountCommand()), TOALLORDERS(new ToAllOrdersCommand()),
	TOSETRECORDSPERPAGEORDER(new ToSetRecordsPerPageOrderCommand()), BACKTOADMINMAIN(new BackToAdminmainCommand()),
	CANCELORDERADMIN(new CancelOrderAdminCommand()), APPROVEADMINORDERCANCEL(new ApproveAdminOrderCancelCommand()), 
	TOSETRECORDSPERPAGECLIENT(new ToSetRecordsPerPageClientCommand()), TOALLCLIENTS(new ToAllClientsCommand()), 
	CHANGEBLACKLIST(new ChangeBlackListCommand()), BACKTOALLORDERS(new BackToAllOrdersCommand()), TOADMINROOMS(new ToAdminRoomsCommand()),
	TOSETRECORDSPERPAGEROOM(new ToSetRecordsPerPageRoomCommand()), BACKTOADMINROOMS(new BackToAdminRoomsCommand()),
	TOALLROOMS(new ToAllRoomsCommand()), CHANGEROOMREMOVED(new ChangeRoomRemovedCommand()), CHANGEROOM(new ChangeRoomCommand()),
	APPROVECHANGEROOM(new ApproveChangeRoomCommand()), BACKTOALLROOMS(new BackToAllRoomsCommand()), TOCREATEROOM(new ToCreateRoomCommand()),
	CREATEROOM(new CreateRoomCommand()), TOADMINNATIONALITIES(new ToAdminNationalitiesCommand()), 
	TOSETRECORDSPERPAGENATIONALITY(new ToSetRecordsPerPageNationalityCommand()), BACKTOADMINNATIONALITIES(new BackToAdminNationalitiesCommand()),
	TOALLNATIONALITIES(new ToAllNationalitiesCommand()), CHANGENATIONALITYREMOVED(new ChangeNationalityRemovedCommand()),
	CHANGENATIONALITY(new ChangeNationalityCommand()), BACKTOALLNATIONALITIES(new BackToAllNationalitiesCommand()),
	APPROVECHANGENATIONALITY(new ApproveChangeNationalityCommand()), TOCREATENATIONALITY(new ToCreateNationalityCommand()),
	CREATENATIONALITY(new CreateNationalityCommand()), TOADMINCLASSES(new ToAdminClassesCommand()),
	TOSETRECORDSPERPAGECLASS(new ToSetRecordsPerPageClassCommand()), BACKTOADMINCLASSES(new BackToAdminClassesCommand()), 
	TOALLCLASSES(new ToAllClassesCommand()), CHANGECLASSREMOVED(new ChangeClassRemovedCommand()), TOCREATECLASS(new ToCreateClassCommand()),
	CREATECLASS(new CreateClassCommand()), TOSETRECORDSPERPAGEACCOUNT(new ToSetRecordsPerPageAccountCommand()),
	TOALLACCOUNTS(new ToAllAccountsCommand()), CHANGEADMINRIGHTS(new ChangeAdminRightsCommand()), BACKTOSIGNUP(new BackToSignupCommand()),
	CHECKKEYANDSIGNUP(new CheckKeyAndSignUpCommand()), TOSETRECORDSPERPAGEEMAIL(new ToSetRecordsPerPageEmailCommand()),
	TOALLEMAILS(new ToAllEmailsCommand()), ADDREMOVETOSENDLIST(new AddRemoveToSendListCommand()), 
	SUBJECTTEXTSEND(new ToSubjectTextSendCommand()), SENDMESSAGE(new SendMessageCommand()), BACKTOALLEMAILS(new BackToAllEmailsCommand());
	
	
	private CommandType(ActionCommand command) {
		this.command = command;
	}

	private ActionCommand command;

	public ActionCommand getCurrentCommand() {
		return command;
	}
}
