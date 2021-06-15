
package hu.edu.greenleaves.bank.model;

import java.math.BigDecimal;
import java.util.Date;

public class TransactionForClient extends AbsEntity {
	private User user;
	private String transCode;
	private StatusForTransaction status;
	private String type_of_transaction;
	private Date dateTime;
	private String toAccountNum;
	private BigDecimal amount;
	private BigDecimal loanAmount;
	private BigDecimal creditCardAmount;

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

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal amount) {
		this.loanAmount = amount;
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

	public BigDecimal getCreditCardAmount() {
		return creditCardAmount;
	}

	public void setCreditCardAmount(BigDecimal creditCardAmount) {
		this.creditCardAmount = creditCardAmount;
	}

	public String getType_of_transaction() {
		return type_of_transaction;
	}

	public void setType_of_transaction(String type_of_transaction) {
		this.type_of_transaction = type_of_transaction;
	}

}
