
package hu.edu.greenleaves.bank.servlet;

import static hu.edu.greenleaves.bank.servlet.ServletPaths.CLIENT_DASHBOARD_PAGE;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hu.edu.greenleaves.bank.commons.ServiceException;
import hu.edu.greenleaves.bank.model.ClientInformation;
import hu.edu.greenleaves.bank.service.ClientInfoDAO;
import hu.edu.greenleaves.bank.service.ClientInfoDAOImpl;

@WebServlet(CLIENT_DASHBOARD_PAGE)
public class ClientDashboardServlet extends DefaultServlet {
	private static final long serialVersionUID = 1L;
	private ClientInfoDAO clientInforDao = new ClientInfoDAOImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			ClientInformation clientInfo = clientInforDao.loadAccountInfo(req.getRemoteUser());
			req.getSession().setAttribute("clientInfo", clientInfo);
		} catch (ServiceException e) {
			sendError(req, e.getMessage());
		}
		forward(req, resp);
	}
}
