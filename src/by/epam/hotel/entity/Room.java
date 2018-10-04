package by.epam.hotel.entity;

import java.math.BigDecimal;

public class Room extends Entity {

	private static final long serialVersionUID = -5676280733157722525L;
	private int number;
	private String classRoom;
	private int capacity;
	private BigDecimal price;

	public Room(int number, String classRoom, int capacity, BigDecimal price, boolean removed) {
		super(removed);
		this.number = number;
		this.classRoom = classRoom;
		this.capacity = capacity;
		this.price = price;
	}
	
	public Room(int number, String classRoom, int capacity, BigDecimal price) {
		super();
		this.number = number;
		this.classRoom = classRoom;
		this.capacity = capacity;
		this.price = price;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getClassRoom() {
		return classRoom;
	}

	public void setClassRoom(String classRoom) {
		this.classRoom = classRoom;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + capacity;
		result = prime * result + ((classRoom == null) ? 0 : classRoom.hashCode());
		result = prime * result + number;
		result = prime * result + ((price == null) ? 0 : price.hashCode());
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
		Room other = (Room) obj;
		if (capacity != other.capacity)
			return false;
		if (classRoom == null) {
			if (other.classRoom != null)
				return false;
		} else if (!classRoom.equals(other.classRoom))
			return false;
		if (number != other.number)
			return false;
		if (price == null) {
			if (other.price != null)
				return false;
		} else if (!price.equals(other.price))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", number=" + number + ", classRoom=" + classRoom + ", capacity=" + capacity
				+ ", price=" + price;
	}
}
