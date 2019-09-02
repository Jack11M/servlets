package com.hillel.servlet;

import com.hillel.model.Role;
import com.hillel.model.Status;
import com.hillel.model.User;
import com.hillel.service.UserService;
import com.hillel.util.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServletTest {
    @InjectMocks
    private UserServlet userServlet;

    @Mock
    private UserService userService;
    @Mock
    private JsonUtil jsonUtil;
    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private PrintWriter writer;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;

    @Before
    public void setUp() throws ServletException {
        initServletConfig();
    }

    @Test
    public void doGet_throwsIOException_returnsStatusCode404WhenUnregisteredUser() throws IOException {
        when(request.getParameter("username")).thenReturn("check");
        userServlet.doGet(request, response);

        verify(response).sendError(404, "Not found : unregistered user");
    }

    @Test
    public void doGet_throwsIOException_returnsStringUserInJson() throws IOException {
        User userAdmin = new User("admin", "admin", "admin",
                "admin", Status.NOT_LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));

        when(request.getParameter("username")).thenReturn("admin");
        when(userService.getUserByUsername("admin")).thenReturn(userAdmin);
        when(response.getWriter()).thenReturn(writer);
        userServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(writer).print(jsonUtil.jsonWriterFromUser(userAdmin));
    }

    private void initServletConfig() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(userServlet.getServletContext().getAttribute("userService")).thenReturn(userService);
        when(userServlet.getServletContext().getAttribute("jsonUtil")).thenReturn(jsonUtil);
        userServlet.init(servletConfig);
    }
}
