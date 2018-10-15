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
import by.epam.hotel.entity.Router;
import by.epam.hotel.exception.CommandException;
import by.epam.hotel.pool.ConnectionPool;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;

public class Controller extends HttpServlet {
	private static final Logger LOG = LogManager.getLogger();
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

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proccedRequest(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		proccedRequest(request, response);
	}

	private void proccedRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession(false);
		if (session == null) {
			response.sendRedirect(request.getContextPath() + 
					ConfigurationManager.getProperty(PropertyConstant.PAGE_WELCOME));
		} else {
			Router router = null;
			String action = request.getParameter(ParameterConstant.COMMAND);
			ActionCommand command = ActionFactory.defineCommand(action);
			try {
				router = command.execute(request);
				LOG.debug(router);
			} catch (CommandException e) {
				LOG.error(router, e);
				response.sendError(500);	
			}
			switch (router.getType()) {
			case REDIRECT:
				response.sendRedirect(request.getContextPath() + router.getPage());
				break;
			case FORWARD:
				request.getRequestDispatcher(router.getPage()).forward(request, response);
				break;
			default:
				throw new IllegalArgumentException("You will never be here");
			}
		}
	}
}
