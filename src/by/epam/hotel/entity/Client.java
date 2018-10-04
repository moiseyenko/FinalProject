package by.epam.hotel.entity;

public class Client extends Entity {

	private static final long serialVersionUID = 722528841853174445L;
	private int id;
	private String firstName;
	private String lastName;
	private String passport;
	private String nationality;
	private boolean blacklist;

	public Client(int id, String firstName, String lastName, String passport, String nationality, boolean blacklist) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passport = passport;
		this.nationality = nationality;
		this.blacklist = blacklist;
	}
	
	public Client(int id, String firstName, String lastName, String passport, String nationality) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.passport = passport;
		this.nationality = nationality;
	}

	public Client(String firstName, String lastName, String passport, String nationality) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.passport = passport;
		this.nationality = nationality;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassport() {
		return passport;
	}

	public void setPassport(String passport) {
		this.passport = passport;
	}

	public String getNationality() {
		return nationality;
	}

	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	
	public boolean isBlacklist() {
		return blacklist;
	}

	public void setBlacklist(boolean blacklist) {
		this.blacklist = blacklist;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + id;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((nationality == null) ? 0 : nationality.hashCode());
		result = prime * result + ((passport == null) ? 0 : passport.hashCode());
		result = prime * result + (blacklist ? 1231 : 1237);
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
		Client other = (Client) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (id != other.id)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (nationality == null) {
			if (other.nationality != null)
				return false;
		} else if (!nationality.equals(other.nationality))
			return false;
		if (passport == null) {
			if (other.passport != null)
				return false;
		} else if (!passport.equals(other.passport))
			return false;
		if (blacklist != other.blacklist)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", passport="
				+ passport + ", nationality=" + nationality +": blacklist=" + blacklist;
	}

}
