package by.epam.hotel.controller;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import by.epam.hotel.command.ActionCommand;
import by.epam.hotel.command.ActionFactory;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.pool.ConnectionPool;
import by.epam.hotel.util.ConfigurationManager;

public class Controller extends HttpServlet {
	private static final Logger LOG = LogManager.getLogger(Controller.class);
	private static final long serialVersionUID = 1L;

	public Controller() {
		super();
	}

	public void init(ServletConfig config) throws ServletException {
		ConnectionPool.getInstance();
	}

	public void destroy() {
		ConnectionPool.getInstance().destroyPool();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proccedRequest(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proccedRequest(request, response);
	}

	private void proccedRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + ConfigurationManager.getProperty("path.page.welcome"));
		} else {
			Router router = null;
			ActionFactory client = new ActionFactory();
			ActionCommand command = client.defineCommand(request);
			try {
				router = command.execute(request);
				LOG.debug(router);
			} catch (CommandException e) {
				System.out.println("in 5000 error");
				LOG.error(router);
				response.sendError(500);
			}
			switch (router.getType()) {
			case REDIRECT:
				response.sendRedirect(request.getContextPath() + router.getPage());
				System.out.println("!!!!!REDIRECT!!!!!!");
				break;
			case FORWARD:
				request.getRequestDispatcher(router.getPage()).forward(request, response);
				System.out.println("!!!!!FORWARD!!!!!!");
				System.out.println(request.getSession().getAttribute("login") + " page not null: "
						+ request.getSession().getAttribute("role"));
				break;
			}
		}
	}
}
