
package hu.edu.greenleaves.bank.service;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.UserRoles;


public interface UserRoleDAO {

	void create(UserRoles userRole) throws ServiceException;

}
