
package hu.edu.greenleaves.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientAccount;

public class ClientAccountDAOImpl extends AbstractDAOImpl implements ClientAccountDAO {


	@Override
	public void create(ClientAccount clientAccount) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "INSERT INTO client_account(user_id, amount, loan_amount, credit_card_amount) VALUES(?,?,?,?)");
			int idx = 1;
			ps.setInt(idx++, clientAccount.getUser().getId());
			ps.setBigDecimal(idx++, clientAccount.getAmount());
			ps.setBigDecimal(idx++, clientAccount.getLoanAmount());
			ps.setBigDecimal(idx++, clientAccount.getCreditCardAmount());
			executeInsert(clientAccount, ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
	public void update(ClientAccount clientAccount) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "UPDATE client_account SET amount = ? WHERE user_id = ?");
			int idx = 1;
			ps.setBigDecimal(idx++, clientAccount.getAmount());
			ps.setInt(idx++, clientAccount.getUser().getId());
			executeUpdate(ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}
	@Override
	public void updateLoan(ClientAccount clientAccount) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "UPDATE client_account SET loan_amount = ? WHERE user_id = ?");
			int idx = 1;
			ps.setBigDecimal(idx++, clientAccount.getLoanAmount());
			ps.setInt(idx++, clientAccount.getUser().getId());
			executeUpdate(ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}
	
	@Override
	public void updateCreditCard(ClientAccount clientAccount) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "UPDATE client_account SET credit_card_amount = ? WHERE user_id = ?");
			int idx = 1;
			ps.setBigDecimal(idx++, clientAccount.getCreditCardAmount());
			ps.setInt(idx++, clientAccount.getUser().getId());
			executeUpdate(ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

}
