package by.epam.hotel.entity;

import by.epam.hotel.util.type.RouterType;

public class Router {
	private String page;
	private RouterType type;
	
	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public RouterType getType() {
		return type;
	}

	public void setType(RouterType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": page=" + page + ", type=" + type;
	}
}
