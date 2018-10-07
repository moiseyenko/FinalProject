package by.epam.hotel.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import by.epam.hotel.entity.Account;
import by.epam.hotel.entity.Client;
import by.epam.hotel.entity.FullInfoOrder;
import by.epam.hotel.entity.Nationality;
import by.epam.hotel.entity.Room;
import by.epam.hotel.entity.RoomClass;
import by.epam.hotel.util.apptype.RoleType;

public class SessionData {
	private String login;
	private RoleType role;
	private Locale locale;
	private boolean innerRedirect;
	private List<Client> clients;
	private List<Nationality> nationalities;
	private List<RoomClass> roomClasses;
	private Client chosenClient;
	List<Room> availableRoomList;
	private LocalDate from;
	private LocalDate to;
	private Room chosenRoom;
	private BigDecimal toPay;
	private BigDecimal currentAmount;
	private List<FullInfoOrder> listAccountFullInfoOrder;
	private List<FullInfoOrder> listAdminFullInfoOrder;
	private List<Client> clientList;
	private List<Room> roomList;
	private List<Nationality> nationalityList;
	private List<RoomClass> roomClassList;
	private List<Account> accountList;
	private int currentPage;
	private int recordsPerPage;
	private int noOfPages;
	private Room roomToChange;
	private Nationality nationalityToChange;
	private String tempLogin;
	private String tempPassword;
	private String tempEmail;
	private String tempEmailKey;
	private Set<Account> sendList;
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public RoleType getRole() {
		return role;
	}

	public void setRole(RoleType role) {
		this.role = role;
	}
	
	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public boolean isInnerRedirect() {
		return innerRedirect;
	}

	public void setInnerRedirect(boolean innerRedirect) {
		this.innerRedirect = innerRedirect;
	}
	
	public List<Client> getClients() {
		return clients;
	}

	public void setClients(List<Client> clients) {
		this.clients = clients;
	}
	
	public List<Nationality> getNationalities() {
		return nationalities;
	}

	public void setNationalities(List<Nationality> nationalities) {
		this.nationalities = nationalities;
	}
	
	public List<RoomClass> getRoomClasses() {
		return roomClasses;
	}

	public void setRoomClasses(List<RoomClass> roomClasses) {
		this.roomClasses = roomClasses;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public Client getChosenClient() {
		return chosenClient;
	}

	public void setChosenClient(Client chosenClient) {
		this.chosenClient = chosenClient;
	}

	public List<Room> getAvailableRoomList() {
		return availableRoomList;
	}

	public void setAvailableRoomList(List<Room> availableRoomList) {
		this.availableRoomList = availableRoomList;
	}

	public Room getChosenRoom() {
		return chosenRoom;
	}

	public void setChosenRoom(Room chosenRoom) {
		this.chosenRoom = chosenRoom;
	}

	public BigDecimal getToPay() {
		return toPay;
	}

	public void setToPay(BigDecimal toPay) {
		this.toPay = toPay;
	}

	public BigDecimal getCurrentAmount() {
		return currentAmount;
	}

	public void setCurrentAmount(BigDecimal currentAmount) {
		this.currentAmount = currentAmount;
	}

	public List<FullInfoOrder> getListAccountFullInfoOrder() {
		return listAccountFullInfoOrder;
	}

	public void setListAccountFullInfoOrder(List<FullInfoOrder> listAccountFullInfoOrder) {
		this.listAccountFullInfoOrder = listAccountFullInfoOrder;
	}

	public List<FullInfoOrder> getListAdminFullInfoOrder() {
		return listAdminFullInfoOrder;
	}

	public void setListAdminFullInfoOrder(List<FullInfoOrder> listAdminFullInfoOrder) {
		this.listAdminFullInfoOrder = listAdminFullInfoOrder;
	}
	
	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setRecordsPerPage(int recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public List<Client> getClientList() {
		return clientList;
	}

	public void setClientList(List<Client> clientList) {
		this.clientList = clientList;
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	public Room getRoomToChange() {
		return roomToChange;
	}

	public void setRoomToChange(Room roomToChange) {
		this.roomToChange = roomToChange;
	}

	public List<Nationality> getNationalityList() {
		return nationalityList;
	}

	public void setNationalityList(List<Nationality> nationalityList) {
		this.nationalityList = nationalityList;
	}

	public Nationality getNationalityToChange() {
		return nationalityToChange;
	}

	public void setNationalityToChange(Nationality nationalityToChange) {
		this.nationalityToChange = nationalityToChange;
	}

	public List<RoomClass> getRoomClassList() {
		return roomClassList;
	}

	public void setRoomClassList(List<RoomClass> roomClassList) {
		this.roomClassList = roomClassList;
	}

	public List<Account> getAccountList() {
		return accountList;
	}

	public void setAccountList(List<Account> accountList) {
		this.accountList = accountList;
	}

	public String getTempLogin() {
		return tempLogin;
	}

	public void setTempLogin(String tempLogin) {
		this.tempLogin = tempLogin;
	}

	public String getTempPassword() {
		return tempPassword;
	}

	public void setTempPassword(String tempPassword) {
		this.tempPassword = tempPassword;
	}

	public String getTempEmail() {
		return tempEmail;
	}

	public void setTempEmail(String tempEmail) {
		this.tempEmail = tempEmail;
	}

	public String getTempEmailKey() {
		return tempEmailKey;
	}

	public void setTempEmailKey(String tempEmailKey) {
		this.tempEmailKey = tempEmailKey;
	}

	public Set<Account> getSendList() {
		return sendList;
	}

	public void setSendList(Set<Account> sendList) {
		this.sendList = sendList;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": login=" + login + ", role=" + role + ", locale=" + locale
				+ ", innerRedirect=" + innerRedirect;
	}

}