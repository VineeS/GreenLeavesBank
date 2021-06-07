/*
 * @author: vineeS
 */

package hu.edu.greenleaves.bank.servlet;
import static hu.edu.greenleaves.bank.servlet.ServletPaths.ADMIN_DASHBOARD_PAGE;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.edu.greenleaves.bank.commons.Constants;
import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.commons.StringUtils;
import hu.edu.greenleaves.bank.model.ClientAccount;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.model.TransactionForClient;
import hu.edu.greenleaves.bank.model.StatusForTransaction;
import hu.edu.greenleaves.bank.model.User;
import hu.edu.greenleaves.bank.model.UserStatus;
import hu.edu.greenleaves.bank.service.ClientAccountDAO;
import hu.edu.greenleaves.bank.service.ClientAccountDAOImpl;
import hu.edu.greenleaves.bank.service.ClientInfoDAO;
import hu.edu.greenleaves.bank.service.ClientInfoDAOImpl;
import hu.edu.greenleaves.bank.service.ClientTransactionDAO;
import hu.edu.greenleaves.bank.service.ClientTransactionDAOImpl;
import hu.edu.greenleaves.bank.service.EmailService;
import hu.edu.greenleaves.bank.service.EmailServiceImp;
import hu.edu.greenleaves.bank.service.TransactionCodesDAO;
import hu.edu.greenleaves.bank.service.TransactionCodesDAOImp;
import hu.edu.greenleaves.bank.service.UserDAO;
import hu.edu.greenleaves.bank.service.UserDAOImpl;

@WebServlet(ADMIN_DASHBOARD_PAGE)
public class AdminDashboardServlet extends DefaultServlet {
	public static final String REGISTRATION_DECISION_ACTION = "registrationDecisionAction";
	public static final String TRANSACTION_DECSION_ACTION = "transactionDecisionAction";
	
	private static final long serialVersionUID = 1L;
	private ClientInfoDAO clientInfoDAO = new ClientInfoDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private ClientAccountDAO clientAccountDAO = new ClientAccountDAOImpl();
	private EmailService emailService = new EmailServiceImp();
	private TransactionCodesDAO transactionCodesDAO = new TransactionCodesDAOImp();
	private ClientTransactionDAO clientTransactionDAO = new ClientTransactionDAOImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			List<ClientInformation> accountList = clientInfoDAO.loadWaitingList();
			req.getSession().setAttribute("registrationList", accountList);
			List<TransactionForClient> transList = clientTransactionDAO.loadWaitingList();
			req.getSession().setAttribute("transList", transList);
		} catch (ServiceException e) {
			sendError(req, e.getMessage());
		}
		forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String actionType = req.getParameter("actionType");
		if (REGISTRATION_DECISION_ACTION.endsWith(actionType)) {
			try {
				onRegistrationDecisionAction(req, resp);
			} catch (ServiceException e) {
				sendError(req, e.getMessage());
				redirect(resp, ADMIN_DASHBOARD_PAGE);
			}
		} else if (TRANSACTION_DECSION_ACTION.equals(actionType)) {
			onTransactionDecisionAction(req, resp);
		}
	}

	private void onRegistrationDecisionAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException, ServiceException {
		String[] decisions = req.getParameterValues("decision");
		int[] userIds = toIntegerArray(req.getParameterValues("user_id"));
		String[] userEmails = req.getParameterValues("user_email");
		List<User> users = new ArrayList<User>();
		for (int i = 0; i < userIds.length; i++) {
			int userId = userIds[i];
			Decision decision = Decision.valueOf(decisions[i]);
			if (decision.getStatus() != null) {
				User user = new User();
				user.setId(userId);
				user.setStatus(decision.getStatus());
				users.add(user);
			}
		}
		if (!users.isEmpty()) {
			try {
				userDAO.updateDecision(users);
			} catch (ServiceException e) {
				sendError(req, e.getMessage());
			}
			activateAccount(userEmails, userIds, decisions);
		}
		redirect(resp, ADMIN_DASHBOARD_PAGE);
	}
	
	private int[] toIntegerArray(String[] idStrs) {
		int[] result = new int[idStrs.length];
		for (int i = 0; i < idStrs.length; i++) {
			result[i] = Integer.valueOf(idStrs[i]);
		}
		return result;
	}

	private void activateAccount(String[] userEmails, int[] userIds, String[] decisions) throws ServiceException {
		for (int i = 0; i < userIds.length; i++) {
			if (Decision.valueOf(decisions[i]) == Decision.approve) {
				int userId = userIds[i];
				/* init account */
				ClientAccount clientAccount = new ClientAccount();
				clientAccount.setUser(new User(userId));
				clientAccount.setAmount(Constants.INIT_AMOUNT);
				clientAccountDAO.create(clientAccount);
				/* generate and send transaction codes */
				List<String> codes = TransactionCodeGenerator.generateCodes(100);
				transactionCodesDAO.create(codes, userId);
				emailService.sendMail(userEmails[i], "Your account has been approved ",
						"Congratulation, your account has been approved! These are your transaction codes: \n"
								+ StringUtils.join(codes, "\n"));
			}
		}
	}

	private BigDecimal[] convertStringtoBigDecimal(String[] list) {
        BigDecimal montanttt[] = new BigDecimal[list.length];
        for (int i=0; i < list.length; i++) {
            try {
                montanttt[i] = new BigDecimal(list[i]);
            } catch (NumberFormatException e) {
                System.out.println("Exception while parsing: " + list[i]);
            }
        }
        return montanttt;
    }

	private void onTransactionDecisionAction(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String[] decisions = req.getParameterValues("decision");
		int[] transIds = toIntegerArray(req.getParameterValues("trans_id"));
		String[] transAccNum = req.getParameterValues("trans_toAccountNum");
        BigDecimal[] transAmount = convertStringtoBigDecimal(req.getParameterValues("trans_amount"));

		List<TransactionForClient> transactions = new ArrayList<TransactionForClient>();
		for (int i = 0; i < transIds.length; i++) {
			int transId = transIds[i];
			Decision decision = Decision.valueOf(decisions[i]);
			if (decision.getStatus() != null) {
				TransactionForClient trans = new TransactionForClient();
				trans.setToAccountNum(transAccNum[i]);
				trans.setAmount(transAmount[i]);
				trans.setId(transId);
				trans.setStatus(decision.getTransStatus());
				if (decision.getStatus().name().equals("APPROVED")) {
                    try {
                        clientTransactionDAO.updateReceiver(trans);
                        clientTransactionDAO.updateSender(trans);
                    } catch (ServiceException e) {
                        sendError(req, e.getMessage());
                    }
                }

				transactions.add(trans);
			}
		}
		if (!transactions.isEmpty()) {
			try {
				clientTransactionDAO.updateDecision(transactions);
			} catch (ServiceException e) {
				sendError(req, e.getMessage());
			}
		}
		redirect(resp, ADMIN_DASHBOARD_PAGE);
	}
	
	private static enum Decision {
		waiting(null, null), 
		approve(UserStatus.APPROVED, StatusForTransaction.APPROVED), 
		decline(UserStatus.DECLINED, StatusForTransaction.DECLINED);
		
		private UserStatus status;
		private StatusForTransaction transStatus;
		private Decision(UserStatus status, StatusForTransaction transStatus) {
			this.status = status;
			this.transStatus = transStatus;
		}

		public UserStatus getStatus() {
			return status;
		}
		
		public StatusForTransaction getTransStatus() {
			return transStatus;
		}
	}
}
