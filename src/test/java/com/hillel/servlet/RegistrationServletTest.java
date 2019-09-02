package com.hillel.servlet;

import com.hillel.model.User;
import com.hillel.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RegistrationServletTest {
    @InjectMocks
    private RegistrationServlet registrationServlet;

    @Mock
    UserService userService;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;

    @Before
    public void setUp() throws ServletException {
        initServletConfig();
    }

    @Test
    public void doGet_throwsServletExceptionAndIOException_returnsRegistrationPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("../WEB-INF/jsp/registration.jsp")).thenReturn(dispatcher);
        registrationServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPost_throwsIOException_returnsStatusCode400WhenParametersAreEmpty() throws IOException {
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        when(request.getParameter("firstName")).thenReturn("");
        registrationServlet.doPost(request, response);

        verify(response).sendError(400, "Bad request");
    }

    @Test
    public void doPost_throwsIOException_returnsStatusCode400WhenUserExists() throws IOException {
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(request.getParameter("firstName")).thenReturn("admin");
        when(!userService.usernameExists("admin")).thenReturn(true);
        registrationServlet.doPost(request, response);

        verify(response).sendError(400, "Bad request : username already exists");
    }

    @Test
    public void doPost_throwsIOException_registrationOfUserAndReturnsLoginPage() throws IOException {
        User newUser = new User("test", "test", "test", "test");

        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(request.getParameter("firstName")).thenReturn("test");
        when(request.getParameter("lastName")).thenReturn("test");
        when(!userService.usernameExists("test")).thenReturn(false);
        when(request.getContextPath()).thenReturn("/servlets1");
        when(userService.createUser(newUser)).thenReturn(true);
        registrationServlet.doPost(request, response);

        verify(response).sendRedirect("/servlets1/user/login");
    }

    private void initServletConfig() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(registrationServlet.getServletContext().getAttribute("userService")).thenReturn(userService);
        registrationServlet.init(servletConfig);
    }
}
