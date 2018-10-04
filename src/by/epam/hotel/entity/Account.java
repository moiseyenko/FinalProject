package by.epam.hotel.entity;

public class Account extends Entity {
	
	private static final long serialVersionUID = 5888722997631088291L;
	private int id;
	private String login;
	private boolean admin;
	private String email;

	public Account(int id, String login, String email, boolean admin, boolean removed) {
		super(removed);
		this.id = id;
		this.login = login;
		this.email = email;
		this.admin = admin;
	}
	
	public Account(int id, String login, String email) {
		super();
		this.id = id;
		this.login = login;
		this.email = email;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + (admin ? 1231 : 1237);
		result = prime * result + id;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
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
		Account other = (Account) obj;
		if (admin != other.admin)
			return false;
		if (id != other.id)
			return false;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " id=" + id + ", login=" + login + ", email=" + email +", admin=" + admin;
	}

	
}
