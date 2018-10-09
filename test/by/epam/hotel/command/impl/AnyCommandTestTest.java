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
import org.junit.Test;

import by.epam.hotel.controller.Controller;
import by.epam.hotel.controller.SessionData;
import by.epam.hotel.exception.ServiceException;
import by.epam.hotel.service.ClientService;
import by.epam.hotel.util.ConfigurationManager;
import by.epam.hotel.util.constant.AttributeConstant;
import by.epam.hotel.util.constant.ParameterConstant;
import by.epam.hotel.util.constant.PropertyConstant;
import by.epam.hotel.util.type.RoleType;

public class AnyCommandTestTest {

	
	
	
	@Test
	public void test() throws ServletException, IOException, ServiceException {
		final Controller servlet = new Controller();
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
		final HttpSession session = mock(HttpSession.class);
		
		when(request.getSession(false)).thenReturn(session);
		when(request.getParameter("command")).thenReturn("replenish");
		when(request.getSession()).thenReturn(session);
		when(request.getContextPath()).thenReturn("/Hotel");
		when(request.getRequestDispatcher(ConfigurationManager.getProperty(PropertyConstant.PAGE_REPLENISH_PAGE))).thenReturn(dispatcher);
		SessionData sessionData = new SessionData();
		sessionData.setRole(RoleType.CLIENT);
		BigDecimal currentAmount = new BigDecimal("0");
		sessionData.setCurrentAmount(currentAmount);
		when(ClientService.updateBankAccount(sessionData.getLogin(), currentAmount)).thenReturn(true);
		when(session.getAttribute(AttributeConstant.SESSION_DATA)).thenReturn(sessionData);

		when(request.getParameter(ParameterConstant.REPLENISH_AMOUNT)).thenReturn("125:33");
		servlet.doPost(request, response);
		
		verify(dispatcher).forward(request, response);
	}

}
