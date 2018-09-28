package by.epam.hotel.controller;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import by.epam.hotel.dao.entity.Client;
import by.epam.hotel.dao.entity.Nationality;
import by.epam.hotel.dao.entity.Room;
import by.epam.hotel.dao.entity.RoomClass;

public class SessionData {
	private String login;
	private RoleType role;
	private LanguageType locale;
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

	public LanguageType getLocale() {
		return locale;
	}

	public void setLocale(LanguageType locale) {
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

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": login=" + login + ", role=" + role + ", locale=" + locale
				+ ", innerRedirect=" + innerRedirect;
	}

}