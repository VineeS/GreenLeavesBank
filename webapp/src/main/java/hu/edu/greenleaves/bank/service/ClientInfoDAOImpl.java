
package hu.edu.greenleaves.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientAccount;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.model.TransactionForClient;
import hu.edu.greenleaves.bank.model.User;


public class ClientInfoDAOImpl extends AbstractDAOImpl implements ClientInfoDAO {
	private ClientTransactionDAO transactionDAO = new ClientTransactionDAOImpl();

	@Override
	public void create(ClientInformation account) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		try {
			ps = prepareStmt(conn, "INSERT INTO CLIENT_INFO(full_name, fin, date_of_birth, occupation, mobile_number, address, email, user_id)"
					+ " VALUES(?,?,?,?,?,?,?,?)");
			int idx = 1;
			ps.setString(idx++, account.getFullName());
			ps.setString(idx++, account.getFin());
			ps.setDate(idx++, account.getDateOfBirth());
			ps.setString(idx++, account.getOccupation());
			ps.setString(idx++, account.getMobileNumber());
			ps.setString(idx++, account.getAddress());
			ps.setString(idx++, account.getEmail());
			ps.setInt(idx++, account.getUser().getId());
			executeInsert(account, ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, null);
		}
	}

	@Override
	public List<ClientInformation> loadWaitingList() throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement("SELECT acc.*, u.* FROM client_info acc, user u WHERE acc.user_id = u.id and u.status is null");
			rs = ps.executeQuery();
			List<ClientInformation> result = new ArrayList<ClientInformation>();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("u.id"));
				user.setUserName(rs.getString("u.user_name"));
				user.setStatus(rs.getString("u.status"));
				ClientInformation clientAccount = new ClientInformation();
				clientAccount.setId(rs.getInt("acc.id"));
				clientAccount.setFullName(rs.getString("acc.full_name"));
				clientAccount.setFin(rs.getString("acc.fin"));
				clientAccount.setDateOfBirth(rs.getDate("acc.date_of_birth"));
				clientAccount.setOccupation(rs.getString("acc.occupation"));
				clientAccount.setMobileNumber(rs.getString("acc.mobile_number"));
				clientAccount.setAddress(rs.getString("acc.address"));
				clientAccount.setEmail(rs.getString("acc.email"));
				clientAccount.setUser(user);
				result.add(clientAccount);
			}
			return result;
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
	public ClientInformation loadAccountInfo(String userName) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		ClientInformation clientInfo = null;
		try {
			ps = conn.prepareStatement(
					"SELECT info.*, acc.*, u.* FROM client_info info, user u, client_account acc WHERE acc.user_id = u.id and info.user_id = u.id and u.user_name=?");
			int idx = 1;
			ps.setString(idx ++, userName);
			rs = ps.executeQuery();
			if (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("u.id"));
				user.setUserName(rs.getString("u.user_name"));
				ClientAccount account = new ClientAccount();
				account.setUser(user);
				account.setId(rs.getInt("acc.id"));
				account.setAmount(rs.getBigDecimal("acc.amount"));
				account.setLoanAmount(rs.getBigDecimal("acc.loan_amount"));
				account.setCreditCardAmount(rs.getBigDecimal("acc.credit_card_amount"));
				clientInfo = new ClientInformation();
				clientInfo.setId(rs.getInt("info.id"));
				clientInfo.setUser(user);
				clientInfo.setAccount(account);
			} else {
				throw new SQLException("No data found for user " + userName);
			}
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
		if (clientInfo != null) {
			List<TransactionForClient> transactions = transactionDAO.load(clientInfo.getUser());
			clientInfo.setTransactions(transactions);
		}
		return clientInfo;
	}
	
	
}
