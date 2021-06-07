
package hu.edu.greenleaves.bank.model;


public class User extends AbsEntity {
	private String userName;
	private String password;
	private UserStatus status;

	public User() {
	}

	public User(int userId) {
		setId(userId);
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public UserStatus getStatus() {
		return status;
	}

	public void setStatus(UserStatus status) {
		this.status = status;
	}

	public void setStatus(String str) {
		UserStatus status = null;
		if (str != null) {
			status = UserStatus.valueOf(str);
		}
		setStatus(status);
	}
	
}
