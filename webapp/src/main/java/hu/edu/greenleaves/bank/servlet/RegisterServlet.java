/*
/*
 * @author: vineeS
 */

package hu.edu.greenleaves.bank.servlet;

import java.io.IOException;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.model.Role;
import hu.edu.greenleaves.bank.model.User;
import hu.edu.greenleaves.bank.model.UserRoles;
import hu.edu.greenleaves.bank.service.ClientInfoDAO;
import hu.edu.greenleaves.bank.service.ClientInfoDAOImpl;
import hu.edu.greenleaves.bank.service.EmailService;
import hu.edu.greenleaves.bank.service.EmailServiceImp;
import hu.edu.greenleaves.bank.service.UserDAO;
import hu.edu.greenleaves.bank.service.UserDAOImpl;
import hu.edu.greenleaves.bank.service.UserRoleDAO;
import hu.edu.greenleaves.bank.service.UserRoleDAOImpl;


@WebServlet("/register")
public class RegisterServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private ClientInfoDAO clientAccountDAO = new ClientInfoDAOImpl();
	private UserDAO userDAO = new UserDAOImpl();
	private UserRoleDAO userRoleDAO = new UserRoleDAOImpl();
	private EmailService emailService = new EmailServiceImp();

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		User user = new User();
		user.setUserName(request.getParameter("username"));
		user.setPassword(request.getParameter("password"));

		ClientInformation client_account = new ClientInformation();
		client_account.setFullName(request.getParameter("fullName"));
		client_account.setFin(request.getParameter("ssn"));
		client_account.setDateOfBirth(Date.valueOf(request.getParameter("dateOfBirth")));
		client_account.setOccupation(request.getParameter("occupation"));
		client_account.setMobileNumber(request.getParameter("mobileNumber"));
		client_account.setAddress(request.getParameter("address"));
		client_account.setEmail(request.getParameter("email"));
		client_account.setUser(user);
		
		try {
			userDAO.create(user);
			clientAccountDAO.create(client_account);
			UserRoles userRole = new UserRoles();
			userRole.setUser(user);
			userRole.setRole(Role.client);
			userRoleDAO.create(userRole);
			emailService.sendMail(client_account.getEmail(), "Green Leaves Bank registration", "Thank you for the registration!");
			sendMsg(request, "You are successfully registered...");
			redirect(response, ServletPaths.WELCOME);
		} catch (ServiceException e) {
			sendError(request, e.getMessage());
			forward(request, response);
		}
	}
}
