package by.epam.hotel.command.impl;

import org.junit.Test;

import by.epam.hotel.controller.Controller;

import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AnyCommandTest {

	@Test
	public void test() throws IOException, ServletException {
		
		final Controller servlet = new Controller();
		final HttpServletRequest request = mock(HttpServletRequest.class);
		final HttpServletResponse response = mock(HttpServletResponse.class);
		final RequestDispatcher diaptcher = mock(RequestDispatcher.class);
		final HttpSession session = mock(HttpSession.class);
		when(request.getSession(false)).thenReturn(null);
		when(request.getContextPath()).thenReturn("/Hotel");
		servlet.doPost(request, response);
		verify(response).sendRedirect("/Hotel/jsp/common/welcome.jsp");
		
		
	}

}
