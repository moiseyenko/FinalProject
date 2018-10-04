package by.epam.hotel.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Order extends Entity {

	private static final long serialVersionUID = 3883259355061810087L;
	private int id;
	private int room;
	private int account;
	private int client;
	private LocalDate from;
	private LocalDate to;
	private BigDecimal cost;

	public Order(int id, int room, int account, int client, LocalDate from, LocalDate to, BigDecimal cost,
			boolean removed) {
		super(removed);
		this.id = id;
		this.room = room;
		this.account = account;
		this.client = client;
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	public Order(int room, int account, int client, LocalDate from, LocalDate to, BigDecimal cost) {
		super();
		this.room = room;
		this.account = account;
		this.client = client;
		this.from = from;
		this.to = to;
		this.cost = cost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}

	public int getAccount() {
		return account;
	}

	public void setAccount(int account) {
		this.account = account;
	}

	public int getClient() {
		return client;
	}

	public void setClient(int client) {
		this.client = client;
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

	public BigDecimal getCost() {
		return cost;
	}

	public void setCost(BigDecimal cost) {
		this.cost = cost;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + account;
		result = prime * result + client;
		result = prime * result + ((from == null) ? 0 : from.hashCode());
		result = prime * result + id;
		result = prime * result + room;
		result = prime * result + ((to == null) ? 0 : to.hashCode());
		result = prime * result + ((cost == null) ? 0 : cost.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		Order other = (Order) obj;
		if (account != other.account)
			return false;
		if (client != other.client)
			return false;
		if (from == null) {
			if (other.from != null)
				return false;
		} else if (!from.equals(other.from))
			return false;
		if (id != other.id)
			return false;
		if (room != other.room)
			return false;
		if (to == null) {
			if (other.to != null)
				return false;
		} else if (!to.equals(other.to))
			return false;
		if (cost == null) {
			if (other.cost != null) {
				return false;
			}
		} else if (!cost.equals(other.cost)) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", id=" + id + ", room=" + room + ", account=" + account + ", client=" + client
				+ ", from=" + from + ", to=" + to + ", cost=" + cost;
	}
}
