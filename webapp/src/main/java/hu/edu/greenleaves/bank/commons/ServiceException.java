package hu.edu.greenleaves.bank.commons;


public class ServiceException extends Exception {
	private static final long serialVersionUID = 1L;

	public ServiceException(Throwable ex) {
		super(ex);
	}

	public static ServiceException wrap(Throwable e) {
		return new ServiceException(e);
	}
}
