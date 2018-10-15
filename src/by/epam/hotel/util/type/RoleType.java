package by.epam.hotel.util.type;

/**
 * This enum has all posible types of user's role.
 * 
 * 
 * @author Evgeniy Moiseyenko
 */
public enum RoleType {
	/**
	 * This role is set to all site visitors
	 */
	GUEST("guest"),
	/**
	 * This role is assigned to all users who are registered in the application as "client"
	 * and allows them to make orders.
	 */
	CLIENT("client"), 
	/**
	 * This role is assigned to all users who are registered in the application as "admin"
	 * and allows them to manage hotel activity.
	 */
	ADMIN("admin");

	private String value;

	private RoleType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
