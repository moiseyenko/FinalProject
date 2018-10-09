package by.epam.hotel.command.impl;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.math.BigDecimal;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import by.epam.hotel.controller.Controller;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.MessageManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;

public class AnyCommandTestTestTest {
	static Controller servlet ;
	static HttpServletRequest request;
	static HttpServletResponse response ;
	static RequestDispatcher dispatcher ;
	static HttpSession session ;
	static ClientService clientService;
	SessionData sessionData;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		servlet = new Controller();
		request = mock(HttpServletRequest.class);
		response = mock(HttpServletResponse.class);
		dispatcher = mock(RequestDispatcher.class);
		session = mock(HttpSession.class);
		clientService = mock(ClientService.class);
	}

	@Before
	public void setUp() throws Exception {
		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("command")).thenReturn("replenish");
		when(request.getSession()).thenReturn(session);
		when(request.getContextPath()).thenReturn("/Hotel");
		sessionData = new SessionData();
		when(session.getAttribute(AttributeConstant.SESSION_DATA)).thenReturn(sessionData);
	}

	@Test
	public void testPositiveReplenish() throws ServiceException, ServletException, IOException {
		
		sessionData.setRole(RoleType.CLIENT);
		when(request.getParameter(ParameterConstant.REPLENISH_AMOUNT)).thenReturn("125,33");
		BigDecimal currentAmount = new BigDecimal(0);
		sessionData.setCurrentAmount(currentAmount);
		sessionData.setLogin("ustas");
		/*when(ClientService.updateBankAccount(sessionData.getLogin(), currentAmount)).thenReturn(true);*/
		when(request.getRequestDispatcher(ConfigurationManager.getProperty(PropertyConstant.PAGE_REPLENISH_PAGE))).thenReturn(dispatcher);
		servlet.doPost(request, response);
		verify(request).setAttribute(AttributeConstant.ERROR_REPLENISH_MESSAGE,
				MessageManager.getProrerty(PropertyConstant.MESSAGE_REPLENISH_ERROR, sessionData.getLocale()));
		verify(dispatcher).forward(request, response);
	}

}
