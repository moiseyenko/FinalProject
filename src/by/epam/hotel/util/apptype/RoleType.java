package by.epam.hotel.util.apptype;

public enum RoleType {
	GUEST("guest"), CLIENT("client"), ADMIN("admin");

	private String value;

	private RoleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
