
package hu.edu.greenleaves.bank.service;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientAccount;


public interface ClientAccountDAO {

	void create(ClientAccount clientAccount) throws ServiceException;

	void update(ClientAccount clientAccount) throws ServiceException;

}
