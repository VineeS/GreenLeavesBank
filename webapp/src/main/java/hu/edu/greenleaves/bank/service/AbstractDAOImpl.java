

package hu.edu.greenleaves.bank.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.AbsEntity;


public abstract class AbstractDAOImpl {
	protected static String driverClassName;
	protected static String dbUrl;
	protected static String schemaUrl;
	protected static String schema;
	protected static String username;
	protected static String password;
	
	static {
		ResourceBundle bundle = ResourceBundle.getBundle("database");
		driverClassName = bundle.getString("jdbc.driverClassName");
		dbUrl = bundle.getString("jdbc.url");
		schema = bundle.getString("jdbc.schema");
		schemaUrl = dbUrl + schema;
		username = bundle.getString("jdbc.username");
		password = bundle.getString("jdbc.password");
	}
	
	protected void executeInsert(AbsEntity entity, PreparedStatement ps) throws SQLException {
		int rowNum = ps.executeUpdate();
		if (rowNum == 0) {
			throw new SQLException("Update failed, no rows affected!");
		}
		try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
			if (generatedKeys.next()) {
				entity.setId(generatedKeys.getInt(1));
			} else {
				throw new SQLException("Update failed, no ID obtained.");
			}
		}
	}
	
	protected void executeUpdate(PreparedStatement ps) throws SQLException {
		int rowNum = ps.executeUpdate();
		if (rowNum == 0) {
			throw new SQLException("Update failed, no rows affected!");
		}
	}
	
	protected PreparedStatement prepareStmt(Connection conn, String query) throws SQLException {
		return conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
	}
	
	public static Connection connectDB() throws ServiceException {
		try {
			Class.forName(driverClassName);
			Connection conn = DriverManager.getConnection(schemaUrl, username, password);
			return conn;
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} catch (ClassNotFoundException e) {
			throw ServiceException.wrap(e);
		}
	}
	
	protected static void closeDb(Connection connection, Statement statement, ResultSet resultSet) {
		if (resultSet != null)
			try {
				resultSet.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (statement != null)
			try {
				statement.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		if (connection != null)
			try {
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
}
