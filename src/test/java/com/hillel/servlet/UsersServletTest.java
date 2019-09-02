package com.hillel.servlet;

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
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UsersServletTest {
    @InjectMocks
    private UsersServlet usersServlet;

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
    public void doGet_throwsIOExceptionAndServletException_returnsStatusCode404WhenUserListIsEvpty() throws IOException {
        when(userService.getUsers()).thenReturn(null);
        usersServlet.doGet(request, response);

        verify(response).sendError(404, "Not found");
    }

    @Test
    public void doGet_throwsIOExceptionAndServletException_returnsStringWithListOfUsers() throws IOException {
        List<User> users = new ArrayList<>();
        users.add(new User("Jack", "Petrov", "jack", "jack"));
        users.add(new User("Nick", "nick", "nick"));

        when(response.getWriter()).thenReturn(writer);
        usersServlet.doGet(request, response);

        verify(response).setContentType("application/json");
        verify(response).setCharacterEncoding("UTF-8");
        verify(writer).print(jsonUtil.jsonWriterFromListOfUsers(users));
    }

    private void initServletConfig() throws ServletException {
        when(servletConfig.getServletContext()).thenReturn(servletContext);
        when(usersServlet.getServletContext().getAttribute("userService")).thenReturn(userService);
        when(usersServlet.getServletContext().getAttribute("jsonUtil")).thenReturn(jsonUtil);
        usersServlet.init(servletConfig);
    }
}
