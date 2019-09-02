package com.hillel.filter;

import com.hillel.dto.UserDto;
import com.hillel.model.Role;
import com.hillel.model.Status;
import org.junit.Before;
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
import java.util.Collections;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccessUserFilterTest {
    @InjectMocks
    private AccessUserFilter accessUserFilter;

    @Mock
    private HttpServletRequest request;
    @Mock
    private HttpServletResponse response;
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain filterChain;

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsLoginPageWhenUserIsNotLoggedIn() throws ServletException, IOException {
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(null);
        when(request.getRequestURI()).thenReturn("test");
        when(request.getContextPath()).thenReturn("/servlets1");
        accessUserFilter.doFilter(request, response, filterChain);

        verify(response).sendRedirect("/servlets1/user/login");
    }

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsStatusCode401WhenUserUnAuthorized() throws ServletException, IOException {
        UserDto userDto = new UserDto("userDto", "userDto", "userDto", Status.LOGGED_IN, Collections.emptyList());

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(userDto);
        when(request.getRequestURI()).thenReturn("test");
        accessUserFilter.doFilter(request, response, filterChain);

        verify(response).sendError(401, "Unauthorized : only USER can use this resource");
    }

    @Test
    public void doFilter_throwsServletExceptionAndIOException_returnsResourceWhenUserHasRoleUser() throws ServletException, IOException {
        UserDto userDto = new UserDto("test", "test", "test", Status.LOGGED_IN, Collections.singletonList(Role.USER));

        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("userDto")).thenReturn(userDto);
        accessUserFilter.doFilter(request, response, filterChain);

        verify(filterChain).doFilter(request, response);
    }
}
