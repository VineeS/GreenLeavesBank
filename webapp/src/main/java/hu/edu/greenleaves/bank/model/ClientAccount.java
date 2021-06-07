
package hu.edu.greenleaves.bank.model;

import java.math.BigDecimal;

public class ClientAccount extends AbsEntity {
	private User user;
	private BigDecimal amount;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

}
