
package hu.edu.greenleaves.bank.service;

import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;

public interface TransactionCodesDAO {

	void create(List<String> codes, int userId) throws ServiceException;

	void updateUsage(String code, int userId) throws ServiceException;

	Boolean validCode(String code, int userId) throws ServiceException;



}
