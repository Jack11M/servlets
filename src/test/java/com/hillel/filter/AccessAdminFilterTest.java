package com.hillel.filter;

import com.hillel.dto.UserDto;
import com.hillel.model.Role;
import com.hillel.model.Status;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessAdminFilterTest {
    @InjectMocks
    private AccessAdminFilter accessAdminFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain filterChain;

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsStatusCode401WhenUserIsNotLoggedIn() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("test");
        accessAdminFilter.doFilter(request, response, filterChain);

        verify(response).sendError(401, "Unauthorized : only ADMIN can use this resource");
    }

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsStatusCode403WhenUserIsNotAdmin() throws ServletException, IOException {
        UserDto testUserDto = new UserDto("test", "test", "test", Status.LOGGED_IN, Collections.singletonList(Role.USER));

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(testUserDto);
        when(request.getRequestURI()).thenReturn("test");
        accessAdminFilter.doFilter(request, response, filterChain);

        verify(response).sendError(403, "Forbidden : only ADMIN can use this resource");
    }

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsResourceWhenUserIsAdmin() throws ServletException, IOException {
        UserDto adminUserDto = new UserDto("admin", "admin", "admin",
                Status.LOGGED_IN, Arrays.asList(Role.ADMIN, Role.USER));

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(adminUserDto);
        accessAdminFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
