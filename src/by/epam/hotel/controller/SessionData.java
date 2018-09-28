package by.epam.hotel.controller;

import java.util.List;

import by.epam.hotel.dao.entity.Client;
import by.epam.hotel.dao.entity.Nationality;

public class SessionData {
	private String login;
	private RoleType role;
	private LanguageType locale;
	private boolean innerRedirect;
	private List<Client> clients;
	private List<Nationality> nationalities;

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

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": login=" + login + ", role=" + role + ", locale=" + locale
				+ ", innerRedirect=" + innerRedirect;
	}

}