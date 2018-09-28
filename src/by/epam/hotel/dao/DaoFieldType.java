package by.epam.hotel.dao;

public enum DaoFieldType {
	ID("id"), LOGIN("login"), PASSWORD("password"), EMAIL("email"), ADMIN("admin"), REMOVED("removed"), FIRST_NAME("first_name"),
	LAST_NAME("last_name"), PASSPORT("passport"), NATIONALITY_ID("nationality_id"), COUNTRY("country"),
	NUMBER("number"), CLASS("class"), CAPACITY("capacity"), PRICE("price"), ROOM_NUMBER("room_number"),
	ACCOUNT_ID("account_id"), CLIENT_ID("client_id"), FROM("from"), TO("to"), COST("cost"), SUM("sum"), 
	QUANTITY("QUANTITY"), STATUS("status"), MONTH("month");

	private String field;

	DaoFieldType(String field) {
		this.field = field;
	}

	public String getField() {
		return field;
	}
}
