
package hu.edu.greenleaves.bank.model;

public enum StatusForTransaction {
	APPROVED,
	DECLINED;

	public static StatusForTransaction of(String status) {
		if (status == null) {
			return null;
		}
		return valueOf(status);
	}
}
