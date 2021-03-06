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
import java.io.IOException;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AdminServletTest {
    @InjectMocks
    private AdminServlet adminServlet;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private RequestDispatcher dispatcher;

    @Test
    public void doGet_throwsServletExceptionAndIOException_returnsAdminPage() throws ServletException, IOException {
        when(request.getRequestDispatcher("/WEB-INF/jsp/admin.jsp")).thenReturn(dispatcher);
        adminServlet.doGet(request, response);

        verify(dispatcher).forward(request, response);
    }
}
