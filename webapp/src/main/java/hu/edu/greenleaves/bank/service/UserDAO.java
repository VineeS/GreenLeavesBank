
package hu.edu.greenleaves.bank.service;

import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.User;


public interface UserDAO {

	User loadUser(String userName) throws ServiceException;

	void create(User user) throws ServiceException;

	void updateDecision(List<User> users) throws ServiceException;

	void blockUser(User user) throws ServiceException;

}
