package com.hillel.servlet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LogoutServletTest {
    @InjectMocks
    private LogoutServlet logoutServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private HttpSession session;

    @Test
    public void doGet_throwsServletExceptionAndIOException_returnsLogoutPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("../WEB-INF/jsp/logout.jsp")).thenReturn(dispatcher);
        logoutServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPost_throwsServletExceptionAndIOException_returnsUserPage() throws IOException, ServletException {
        when(request.getParameter("logout")).thenReturn("no");
        when(request.getContextPath()).thenReturn("/servlets1");
        logoutServlet.doPost(request, response);

        verify(response).sendRedirect("/servlets1/user/home");
    }

    @Test
    public void doPostAndDoPut_throwsServletExceptionAndIOException_sessionInvalidateAndreturnsIndexPage() throws IOException, ServletException {
        when(request.getParameter("logout")).thenReturn("yes");
        when(request.getSession()).thenReturn(session);
        logoutServlet.doPost(request, response);

        verify(session).invalidate();
        verify(response).sendRedirect("../index.jsp");
    }
}
