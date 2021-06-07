
package hu.edu.greenleaves.bank.service;

import hu.edu.greenleaves.bank.commons.ServiceException;

public interface EmailService {

	void sendMail(String toAddr, String subject, String msg) throws ServiceException;

}
