package com.hillel.servlet;

import com.hillel.model.Role;
import com.hillel.model.Status;
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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class LoginServletTest {
    @InjectMocks
    private LoginServlet loginServlet;

    @Mock
    private UserService userService;
    @Mock
    private User user;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
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
    public void doGet_throwsServletExceptionAndIOException_returnsLoginPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("../WEB-INF/jsp/login.jsp")).thenReturn(dispatcher);
        loginServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }

    @Test
    public void doPost_throwsIOException_returnsStatusCode400WhenParametersAreEmpty() throws IOException {
        when(request.getParameter("username")).thenReturn("");
        when(request.getParameter("password")).thenReturn("");
        loginServlet.doPost(request, response);

        verify(response).sendError(400, "Bad request");
    }

    @Test
    public void doPost_throwsIOException_returnsStatusCode404WhenUnregisteredUser() throws IOException {
        when(request.getParameter("username")).thenReturn("test");
        when(request.getParameter("password")).thenReturn("test");
        when(userService.userExistsByNameAndPass("test", "test")).thenReturn(false);
        when(userService.getUserByUsername("test")).thenReturn(user);
        loginServlet.doPost(request, response);

        verify(response).sendError(404, "Not found : unregistered user");
    }

    @Test
    public void doPostAndDoPut_throwsIOException_returnsSuccessLoginServlet() throws IOException {
        User userAdmin = new User("admin", "admin", "admin",
                "admin", Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));

        when(request.getAttribute("user")).thenReturn(userAdmin);
        when(request.getParameter("username")).thenReturn("admin");
        when(request.getParameter("password")).thenReturn("admin");
        when(this.userService.userExistsByNameAndPass("admin", "admin")).thenReturn(true);
        when(this.userService.getUserByUsername("admin")).thenReturn(userAdmin);
        when(request.getSession()).thenReturn(session);
        when(request.getContextPath()).thenReturn("/servlets1");
        loginServlet.doPost(request, response);

        verify(request).getSession();
        verify(response).sendRedirect("/servlets1/user/home");
    }

    private void initServletConfig() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(loginServlet.getServletContext().getAttribute("userService")).thenReturn(userService);
        loginServlet.init(servletConfig);
    }
}
