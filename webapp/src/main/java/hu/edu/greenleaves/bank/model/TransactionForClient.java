
package hu.edu.greenleaves.bank.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionForClient extends AbsEntity {
	private User user;
	private String transCode;
	private StatusForTransaction status;
	private Date dateTime;
	private String toAccountNum;
	private BigDecimal amount;

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public StatusForTransaction getStatus() {
		return status;
	}

	public void setStatus(StatusForTransaction status) {
		this.status = status;
	}

	public Date getDateTime() {
		return dateTime;
	}

	public void setDateTime(Date dateTime) {
		this.dateTime = dateTime;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToAccountNum() {
		return toAccountNum;
	}

	public void setToAccountNum(String toAccountNum) {
		this.toAccountNum = toAccountNum;
	}

}
