package by.epam.hotel.entity;

public class RoomClass extends Entity{
	
	private static final long serialVersionUID = 6042203087265021796L;
	private String classId;
	
	public RoomClass (String classId, boolean removed) {
		super(removed);
		this.classId = classId;
	}
	
	public RoomClass (String classId) {
		this.classId = classId;
	}

	public String getClassId() {
		return classId;
	}

	public void setClassId(String classId) {
		this.classId = classId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((classId == null) ? 0 : classId.hashCode());
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
		RoomClass other = (RoomClass) obj;
		if (classId == null) {
			if (other.classId != null)
				return false;
		} else if (!classId.equals(other.classId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + "classId=" + classId;
	}
	
	
	
}
