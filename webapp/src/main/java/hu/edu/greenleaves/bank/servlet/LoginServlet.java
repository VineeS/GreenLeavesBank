/*
 * @author: VineeS
 */

package hu.edu.greenleaves.bank.servlet;

import static hu.edu.greenleaves.bank.servlet.ServletPaths.*;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.model.User;
import hu.edu.greenleaves.bank.model.UserStatus;
import hu.edu.greenleaves.bank.service.*;


@WebServlet(LOGIN)
public class LoginServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private UserDAO userDAO = new UserDAOImpl();

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String userName = req.getParameter("username");
			User user = userDAO.loadUser(userName);
			if (user != null && (user.getStatus() == UserStatus.APPROVED)) {
				req.login(userName, req.getParameter("password"));
				HttpSession session = req.getSession(true);
				session.setAttribute("authenticatedUser", req.getRemoteUser());
				setUserId(req, user.getId());
				if (req.isUserInRole("client")) {
					redirect(resp, CLIENT_DASHBOARD_PAGE);
				} else if (req.isUserInRole("Admin")) {
					redirect(resp, ADMIN_DASHBOARD_PAGE);
				}
				return;
			}          

            User user2 = userDAO.loadUser(userName);



			sendError(req, "Invalid username/password!");
		} catch(ServletException | ServiceException ex) {
			sendError(req, ex.getMessage());
		}
		forward(req, resp);
	}

}
