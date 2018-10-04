package by.epam.hotel.entity;

import java.math.BigDecimal;

public class BankAccount extends Entity {

	private static final long serialVersionUID = -4586711303647435791L;
	
	int id;
	BigDecimal amount;

	public BankAccount(int id, BigDecimal amount) {
		this.id = id;
		this.amount = amount;
	}

	public BankAccount(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + id;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
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
		BankAccount other = (BankAccount) obj;
		if (id != other.id)
			return false;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return super.toString() + " id=" + id + ", amount=" + amount + "]";
	}
}
