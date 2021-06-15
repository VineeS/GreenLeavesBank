
package hu.edu.greenleaves.bank.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.TransactionForClient;
import hu.edu.greenleaves.bank.model.StatusForTransaction;
import hu.edu.greenleaves.bank.model.User;

public class ClientTransactionDAOImpl extends AbstractDAOImpl implements ClientTransactionDAO {

	@Override
	public synchronized void create(TransactionForClient clientTransaction) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            ps = prepareStmt(conn, "INSERT INTO client_transaction(trans_code, amount, to_account_num, user_id, type_of_transaction)"
                    + " VALUES(?,?,?,?,?)");
            int idx = 1;
            ps.setString(idx++, clientTransaction.getTransCode());
            ps.setBigDecimal(idx++, clientTransaction.getAmount());
            ps.setString(idx++, clientTransaction.getToAccountNum());
            ps.setInt(idx++, clientTransaction.getUser().getId());
            ps.setString(idx++, "Transfer");
            executeInsert(clientTransaction, ps);

		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
            closeDb(conn, ps, rs);
        }
	}
	@Override
	public synchronized void createLoan(TransactionForClient clientTransaction) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = prepareStmt(conn, "INSERT INTO client_transaction(trans_code, amount, to_account_num, user_id, type_of_transaction)"
                    + " VALUES(?,?,?,?,?)");
            int idx = 1;
            ps.setString(idx++, clientTransaction.getTransCode());
            ps.setBigDecimal(idx++, clientTransaction.getLoanAmount());
            ps.setString(idx++, clientTransaction.getToAccountNum());
            ps.setInt(idx++, clientTransaction.getUser().getId());
            ps.setString(idx++, "Loan");
            executeInsert(clientTransaction, ps);

		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
            closeDb(conn, ps, rs);
        }
	}
	
	@Override
	public synchronized void createCreditCard(TransactionForClient clientTransaction) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = prepareStmt(conn, "INSERT INTO client_transaction(trans_code, amount, to_account_num, user_id, type_of_transaction)"
                    + " VALUES(?,?,?,?,?)");
            int idx = 1;
            ps.setString(idx++, clientTransaction.getTransCode());
            ps.setBigDecimal(idx++, new BigDecimal(10000));
            ps.setString(idx++, clientTransaction.getToAccountNum());
            ps.setInt(idx++, clientTransaction.getUser().getId());
            ps.setString(idx++, "Credit Card");
            executeInsert(clientTransaction, ps);

		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
            closeDb(conn, ps, rs);
        }
	}

	@Override
	public List<TransactionForClient> load(User user) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM client_transaction WHERE user_id = ?");
			int idx = 1;
			ps.setInt(idx++, user.getId());
			rs = ps.executeQuery();
			List<TransactionForClient> transactions = new ArrayList<TransactionForClient>();
			while (rs.next()) {
				TransactionForClient trans = new TransactionForClient();
				trans.setId(rs.getInt("id"));
				trans.setUser(user);
				trans.setAmount(rs.getBigDecimal("amount"));
				trans.setDateTime(rs.getDate("datetime"));
				trans.setType_of_transaction(rs.getString("type_of_transaction"));
				trans.setStatus(StatusForTransaction.of(rs.getString("status")));
				trans.setTransCode(rs.getString("trans_code"));
				trans.setToAccountNum(rs.getString("to_account_num"));
				transactions.add(trans);
			}
			return transactions;
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
	public List<TransactionForClient> loadWaitingList() throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(
					"SELECT * FROM client_transaction WHERE status is null");
			rs = ps.executeQuery();
			List<TransactionForClient> transactions = new ArrayList<TransactionForClient>();
			while (rs.next()) {
				TransactionForClient trans = new TransactionForClient();
				trans.setId(rs.getInt("id"));
				User user = new User(rs.getInt("user_id"));
				trans.setUser(user);
				trans.setAmount(rs.getBigDecimal("amount"));
				trans.setType_of_transaction(rs.getString("type_of_transaction"));
				trans.setDateTime(rs.getDate("datetime"));
				trans.setTransCode(rs.getString("trans_code"));
				trans.setToAccountNum(rs.getString("to_account_num"));
				transactions.add(trans);
			}
			return transactions;
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
    public synchronized void updateSender(TransactionForClient transaction) throws ServiceException {
        Connection conn = connectDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = null;
        System.out.println(transaction.getType_of_transaction());
        if(transaction.getType_of_transaction().equals("Transfer")) {
        	 query = String.format("UPDATE client_account AS a, client_transaction AS b SET a.amount = a.amount - %s WHERE b.id = %s AND a.user_id = b.user_id",
                transaction.getAmount(), transaction.getId());}
        else if (transaction.getType_of_transaction().equals("Loan")) {
			 query = String.format("UPDATE client_account AS a, client_transaction AS b SET a.loan_amount = a.loan_amount + %s WHERE b.id = %s AND a.user_id = b.user_id",
					transaction.getAmount(), transaction.getId());
		}else if (transaction.getType_of_transaction().equals("Credit Card")) {
			 query = String.format("UPDATE client_account AS a, client_transaction AS b SET a.credit_card_amount = 10000 WHERE b.id = %s AND a.user_id = b.user_id",
						 transaction.getId());
		}
        try {
            ps = prepareStmt(conn, query);
            ps.executeUpdate();
            if (false) {
                throw new SQLException("Update failed, no rows here!" + transaction.getId());
            }

        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, rs);
        }
    }

    @Override
    public synchronized void updateReceiver(TransactionForClient transaction) throws ServiceException {
        Connection conn = connectDB();

        PreparedStatement ps = null;
        ResultSet rs = null;
        String query = String.format("UPDATE client_account SET amount = amount + %s WHERE user_id = %s", transaction.getAmount(), transaction.getToAccountNum());

        try {
            ps = prepareStmt(conn, query);
            ps.executeUpdate();
            if (false) {
                throw new SQLException("Update failed, no rows here!" + transaction.getId());
            }

        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, rs);
        }
    }

	@Override
	public synchronized void updateDecision(List<TransactionForClient> transactions) throws ServiceException {
		StringBuilder query = new StringBuilder("UPDATE client_transaction SET status = Case id ");
		for (TransactionForClient trans : transactions) {
			query.append(String.format("WHEN %d THEN '%s' ", trans.getId(), trans.getStatus().name()));
		}
		query.append("ELSE status ")
			.append("END ")
			.append("WHERE id IN(");
		for (int i = 0; i < transactions.size(); i++) {
			query.append(transactions.get(i).getId());
			if (i < transactions.size() - 1) {
				query.append(", ");
			}
		}
		query.append(");");
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, query.toString());
			int rowNum = ps.executeUpdate();
			if (rowNum == 0) {
				throw new SQLException("Update failed, no rows affected!");
			}
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

	@Override
    public synchronized Boolean validTransaction(TransactionForClient transaction) throws  ServiceException {
        Connection conn = connectDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
	    try {
            ps = prepareStmt(conn, "SELECT amount FROM client_account WHERE user_id = ?");
            int idx = 1;
			ps.setString(idx++, String.valueOf(transaction.getUser().getId()));
			rs = ps.executeQuery();
			if (rs.next()) {
                BigDecimal current_amount = new BigDecimal(rs.getInt(1));
                return transaction.getAmount().compareTo(current_amount) < 0;
            } else {
			    throw new SQLException("no data found");
            }

        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, rs);
        }

    }
	
	@Override
    public synchronized Boolean validLoanTransaction(TransactionForClient transaction) throws  ServiceException {
        Connection conn = connectDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
	    try {
            ps = prepareStmt(conn, "SELECT amount FROM client_account WHERE user_id = ?");
            int idx = 1;
			ps.setString(idx++, String.valueOf(transaction.getUser().getId()));
			rs = ps.executeQuery();
			
			if (rs.next()) {
                BigDecimal current_amount = new BigDecimal(rs.getInt(1));
                System.out.println(rs.getInt(1));
                System.out.println(transaction.getLoanAmount().divide(new BigDecimal(2)));
                return current_amount.compareTo((transaction.getLoanAmount().divide(new BigDecimal(2)))) >= 0;
            } else {
			    throw new SQLException("no data found");
            }

        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, rs);
        }

    }
	
	@Override
    public synchronized Boolean validCreditCardApplicationTransaction(TransactionForClient transaction) throws  ServiceException {
        Connection conn = connectDB();
        PreparedStatement ps = null;
        ResultSet rs = null;
	    try {
            ps = prepareStmt(conn, "SELECT amount, credit_card_amount FROM client_account WHERE user_id = ?");
            int idx = 1;
			ps.setString(idx++, String.valueOf(transaction.getUser().getId()));
			rs = ps.executeQuery();
			
			if (rs.next()) {
                BigDecimal current_amount = new BigDecimal(rs.getInt(1));
                BigDecimal credit_card_amount = new BigDecimal(rs.getInt(2));
                System.out.println(rs.getInt(1));
                System.out.println(rs.getInt(2));
                System.out.println(current_amount.compareTo(new BigDecimal(0)) > 0);
                System.out.println(credit_card_amount.compareTo(new BigDecimal(0))==0);
                return current_amount.compareTo(new BigDecimal(0)) > 0 && credit_card_amount.compareTo(new BigDecimal(0))==0 ;
            } else {
			    throw new SQLException("no data found");
            }

        } catch (SQLException e) {
            throw ServiceException.wrap(e);
        } finally {
            closeDb(conn, ps, rs);
        }

    }
}
