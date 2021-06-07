/*
 * @author: vineeS
 */

package hu.edu.greenleaves.bank.servlet;

import static hu.edu.greenleaves.bank.servlet.ServletPaths.NEW_TRANSACTION;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.edu.greenleaves.bank.commons.Helper;
import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.model.TransactionForClient;
import hu.edu.greenleaves.bank.model.User;
import hu.edu.greenleaves.bank.service.*;

import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@WebServlet(NEW_TRANSACTION)
public class NewTransactionServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private ClientTransactionDAO clientTransactionDAO = new ClientTransactionDAOImpl();
	private TransactionCodesDAO transactionCodesDAO = new TransactionCodesDAOImp();


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			TransactionForClient clientTransaction = new TransactionForClient();
			ClientInformation clientInfo = new ClientInformation();
			User user = new User(getUserId(req));
			clientTransaction.setUser(user);
			clientInfo.setUser(user);
			BigDecimal amount = new BigDecimal(req.getParameter("amount"));
			clientTransaction.setAmount(amount);

			//Transcode may be malicious
			String code = req.getParameter("transcode");
			code = Helper.inputNormalizer(code);
			if (Helper.xssMatcher(code)) {
				throw new ServletException("XSS Injection Attempt");
			}
			clientTransaction.setTransCode(code);
			clientTransaction.setToAccountNum(req.getParameter("toAccountNum"));

			if (transactionCodesDAO.validCode(code,clientInfo.getUser().getId()) && clientTransactionDAO.validTransaction(clientTransaction)) {
				transactionCodesDAO.updateUsage(code, clientInfo.getUser().getId());
				clientTransactionDAO.create(clientTransaction);
				redirect(resp, ServletPaths.CLIENT_DASHBOARD_PAGE);
			} else {
				throw new ServletException("Code is invalid or Account does not have enough Balance");
			}
		} catch (ServiceException e) {
			sendError(req, e.getMessage());
			forward(req, resp);
		}
	}

}
