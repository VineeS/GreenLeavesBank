
package hu.edu.greenleaves.bank.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.UserRoles;

public class UserRoleDAOImpl extends AbstractDAOImpl implements UserRoleDAO {


	@Override
	public void create(UserRoles userRole) throws ServiceException {
		Connection conn = connectDB();
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = prepareStmt(conn, "INSERT INTO user_role(user_name, role) VALUES(?,?)");
			int idx = 1;
			ps.setString(idx++, userRole.getUser().getUserName());
			ps.setString(idx++, userRole.getRole().name());
			executeInsert(userRole, ps);
		} catch (SQLException e) {
			throw ServiceException.wrap(e);
		} finally {
			closeDb(conn, ps, rs);
		}
	}

}
