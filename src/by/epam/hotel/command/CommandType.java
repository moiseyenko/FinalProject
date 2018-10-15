package by.epam.hotel.command;

import by.epam.hotel.command.impl.admin.AddRemoveToSendListCommand;
import by.epam.hotel.command.impl.admin.ApproveAdminOrderCancelCommand;
import by.epam.hotel.command.impl.admin.ApproveChangeNationalityCommand;
import by.epam.hotel.command.impl.admin.ApproveChangeRoomCommand;
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
import by.epam.hotel.command.impl.admin.ToAdminRoomsCommand;
import by.epam.hotel.command.impl.admin.ToAllAccountsCommand;
import by.epam.hotel.command.impl.admin.ToAllClassesCommand;
import by.epam.hotel.command.impl.client.ApproveOrderCancelCommand;
import by.epam.hotel.command.impl.client.BackToAccountOrdersCommand;
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
import by.epam.hotel.command.impl.common.BackToSignupCommand;
import by.epam.hotel.command.impl.common.ChangeLocaleCommand;
import by.epam.hotel.command.impl.common.ChangeLoginCommand;
import by.epam.hotel.command.impl.common.ChangePasswordCommand;
import by.epam.hotel.command.impl.common.CheckKeyAndSignUpCommand;
import by.epam.hotel.command.impl.common.DeleteAccountCommand;
import by.epam.hotel.command.impl.common.ErrorBackCommand;
import by.epam.hotel.command.impl.common.HotelInfoCommand;
import by.epam.hotel.command.impl.common.LoginBackCommand;
import by.epam.hotel.command.impl.common.LoginCommand;
import by.epam.hotel.command.impl.common.LogoutCommand;
import by.epam.hotel.command.impl.common.SignUpCommand;
import by.epam.hotel.command.impl.common.SignupBackCommand;
import by.epam.hotel.command.impl.common.ToChangeLoginCommand;
import by.epam.hotel.command.impl.common.ToChangePasswordCommand;
import by.epam.hotel.command.impl.common.ToChangePersonalDataCommand;
import by.epam.hotel.command.impl.common.ToDeleteAccountCommand;
import by.epam.hotel.command.impl.common.ToLoginCommand;
import by.epam.hotel.command.impl.common.ToSighUpCommand;

/**
 * This class is the repository of all action commands used in the application
 * 
 * @author Evgeniy Moiseyenko
 *
 */
public enum CommandType {
	LOGIN(new LoginCommand()), 
	LOGOUT(new LogoutCommand()), 
	SIGNUP(new SignUpCommand()),
	ORDER(new OrderCommand()),
	TO_LOGIN(new ToLoginCommand()),
	TO_SIGNUP(new ToSighUpCommand()),
	BACK_TO_CLIENT_MAIN (new BackToClientmainCommand()),
	LOGIN_BACK(new LoginBackCommand()),
	SIGNUP_BACK(new SignupBackCommand()),
	CHANGE_LOCALE(new ChangeLocaleCommand()),
	FILL_ORDER_FORM(new FillOrderFormCommand()),
	FIND_ROOM(new FindRoomCommand()),
	BACK_TO_ORDER (new BackToOrderCommand()),
	CHOOSE_ROOM(new ChooseRoomCommand()),
	BACK_TO_ROOMS(new BackToRoomsCommand()),
	TO_PAY(new ToPayCommand()),
	BACK_TO_INFOPAYMENT(new BackToInfoPaymentCommnamd()),
	TO_REPLENISH(new ToReplenishCommand()),
	BACK_TO_PAYPAGE(new BackToPayPageCommand()),
	REPLENISH(new ReplenishCommand()),
	PAY(new PayCommand()),
	TO_ACCOUNT_ORDERS(new ToAccountOrdersCommand()),
	CANCEL_ORDER(new CancelOrderCommand()),
	BACK_TO_ACCOUNT_ORDERS(new BackToAccountOrdersCommand()),
	APPROVE_ORDER_CANCEL(new ApproveOrderCancelCommand()), 
	TO_CHANGE_PERSONAL_DATA(new ToChangePersonalDataCommand()),
	TO_CHANGE_LOGIN(new ToChangeLoginCommand()),
	TO_CHANGE_PASSWORD(new ToChangePasswordCommand()),
	TO_DELETE_ACCOUNT(new ToDeleteAccountCommand()),
	CHANGE_LOGIN(new ChangeLoginCommand()), 
	CHANGE_PASSWORD(new ChangePasswordCommand()),
	DELETE_ACCOUNT(new DeleteAccountCommand()),
	TO_ALL_ORDERS(new ToAllOrdersCommand()),
	BACK_TO_ADMIN_MAIN(new BackToAdminmainCommand()),
	CANCEL_ORDER_ADMIN(new CancelOrderAdminCommand()),
	APPROVE_ADMIN_ORDER_CANCEL(new ApproveAdminOrderCancelCommand()), 
	TO_ALL_CLIENTS(new ToAllClientsCommand()), 
	CHANGE_BLACKLIST(new ChangeBlackListCommand()),
	BACK_TO_ALL_ORDERS(new BackToAllOrdersCommand()), 
	TO_ADMIN_ROOMS(new ToAdminRoomsCommand()),
	TO_ALL_ROOMS(new ToAllRoomsCommand()), 
	CHANGE_ROOM_REMOVED(new ChangeRoomRemovedCommand()), 
	CHANGE_ROOM(new ChangeRoomCommand()),
	APPROVE_CHANGE_ROOM(new ApproveChangeRoomCommand()),
	BACK_TO_ALL_ROOMS(new BackToAllRoomsCommand()), 
	TO_CREATE_ROOM(new ToCreateRoomCommand()),
	CREATE_ROOM(new CreateRoomCommand()),
	TO_ADMIN_NATIONALITIES(new ToAdminNationalitiesCommand()), 
	TO_ALL_NATIONALITIES(new ToAllNationalitiesCommand()),
	CHANGE_NATIONALITY_REMOVED(new ChangeNationalityRemovedCommand()),
	CHANGE_NATIONALITY(new ChangeNationalityCommand()),
	BACK_TO_ALL_NATIONALITIES(new BackToAllNationalitiesCommand()),
	APPROVE_CHANGE_NATIONALITY(new ApproveChangeNationalityCommand()),
	TO_CREATE_NATIONALITY(new ToCreateNationalityCommand()),
	CREATE_NATIONALITY(new CreateNationalityCommand()), 
	TO_ADMIN_CLASSES(new ToAdminClassesCommand()),
	TO_ALL_CLASSES(new ToAllClassesCommand()), 
	CHANGE_CLASS_REMOVED(new ChangeClassRemovedCommand()),
	TO_CREATE_CLASS(new ToCreateClassCommand()),
	CREATE_CLASS(new CreateClassCommand()), 
	TO_ALL_ACCOUNTS(new ToAllAccountsCommand()),
	CHANGE_ADMIN_RIGHTS(new ChangeAdminRightsCommand()), 
	BACK_TO_SIGNUP(new BackToSignupCommand()),
	CHECK_KEY_AND_SIGNUP(new CheckKeyAndSignUpCommand()),
	TO_ALL_EMAILS(new ToAllEmailsCommand()), 
	ADD_REMOVE_TO_SENDLIST(new AddRemoveToSendListCommand()), 
	SUBJECT_TEXT_SEND(new ToSubjectTextSendCommand()), 
	SEND_MESSAGE(new SendMessageCommand()), 
	BACK_TO_ALL_EMAILS(new BackToAllEmailsCommand()),
	ERROR_BACK(new ErrorBackCommand()),
	HOTEL_INFO(new HotelInfoCommand());
	
	
	CommandType(ActionCommand command) {
		this.command = command;
	}

	private ActionCommand command;

	/**
	 * Return the implementation of command object
	 * 
	 * 
	 * @return  the implementation of {@link ActionCommand} 
	 */
	public ActionCommand getCurrentCommand() {
		return command;
	}
}
