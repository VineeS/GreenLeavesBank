
package hu.edu.greenleaves.bank.service;

import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.TransactionForClient;
import hu.edu.greenleaves.bank.model.User;


public interface ClientTransactionDAO {

	void create(TransactionForClient clientTransaction) throws ServiceException;

	List<TransactionForClient> load(User user) throws ServiceException;
	
	List<TransactionForClient> loadWaitingList() throws ServiceException;

	void updateDecision(List<TransactionForClient> transactions) throws ServiceException;

	void updateReceiver(TransactionForClient transaction) throws ServiceException;

    void updateSender(TransactionForClient transaction) throws ServiceException;

    Boolean validTransaction(TransactionForClient transaction) throws  ServiceException;

	Boolean validLoanTransaction(TransactionForClient transaction) throws ServiceException;

	void createLoan(TransactionForClient clientTransaction) throws ServiceException;

	void createCreditCard(TransactionForClient clientTransaction) throws ServiceException;

	Boolean validCreditCardApplicationTransaction(TransactionForClient transaction) throws ServiceException;


}
