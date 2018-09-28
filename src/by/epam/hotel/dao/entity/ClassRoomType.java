package by.epam.hotel.dao.entity;

public enum ClassRoomType {
	STANDARD("��������"), FAMILY("��������"), HONEYMOON("��� �����������"), BUSINESS("������"),
	LUX("����"), PRESIDENTIAL_SUITE("������������� ����");
	
	String name;
	private ClassRoomType(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static ClassRoomType fromValue(String v) {
        for (ClassRoomType c: ClassRoomType.values()) {
            if (c.name.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}
