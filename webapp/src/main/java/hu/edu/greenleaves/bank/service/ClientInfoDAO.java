
package hu.edu.greenleaves.bank.service;

import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientInformation;


public interface ClientInfoDAO {

	void create(ClientInformation account) throws ServiceException;

	List<ClientInformation> loadWaitingList() throws ServiceException;

	ClientInformation loadAccountInfo(String userName) throws ServiceException;

}
