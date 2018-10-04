package by.epam.hotel.entity;

public class Nationality extends Entity{
	
	private static final long serialVersionUID = 2013950784253437740L;
	private String countryId;
	private String country;
	
	public Nationality(String countryId, String country, boolean removed) {
		super(removed);
		this.countryId = countryId;
		this.country = country;
	}
	
	public Nationality(String countryId, String country) {
		super();
		this.countryId = countryId;
		this.country = country;
	}
	
	public String getCountryId() {
		return countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((country == null) ? 0 : country.hashCode());
		result = prime * result + ((countryId == null) ? 0 : countryId.hashCode());
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
		Nationality other = (Nationality) obj;
		if (country == null) {
			if (other.country != null)
				return false;
		} else if (!country.equals(other.country))
			return false;
		if (countryId == null) {
			if (other.countryId != null)
				return false;
		} else if (!countryId.equals(other.countryId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + ", countryId=" + countryId + ", country=" + country;
	}
}
