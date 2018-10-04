package by.epam.hotel.entity;

import java.io.Serializable;

public abstract class Entity implements Serializable, Cloneable {
	
	private static final long serialVersionUID = -1719291704527859340L;
	private boolean removed;
	
	public Entity(boolean removed) {
		this.removed = removed;
	}
	
	public Entity() {
	}

	public boolean isRemoved() {
		return removed;
	}

	public void setRemoved(boolean removed) {
		this.removed = removed;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (removed ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Entity other = (Entity) obj;
		if (removed != other.removed)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": removed=" + removed;
	}

	
	
	
}
