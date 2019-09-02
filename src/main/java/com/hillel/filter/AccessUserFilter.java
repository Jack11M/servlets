package com.hillel.filter;

import com.hillel.dto.UserDto;
import com.hillel.model.Role;
import lombok.extern.log4j.Log4j;
import com.hillel.util.StringConstant;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@WebFilter(urlPatterns = {"/user/home", "/user/logout"})
@Log4j
public class AccessUserFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //empty method
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        log.info("AccessUserFilter called");

        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpSession session = req.getSession();
        UserDto userDto = (UserDto) session.getAttribute(StringConstant.USER_DTO);

        if (userDto != null) {
            if (Stream.of(userDto.getRoles()).anyMatch(s -> s.contains(Role.USER))) {
                filterChain.doFilter(servletRequest, servletResponse);
                log.info("AccessUserFilter: access is allowed");
            } else {
                String url = ((HttpServletRequest) servletRequest).getRequestURI();
                resp.sendError(401, StringConstant.UNAUTHORIZED.concat(" : ").concat(StringConstant.ONLY_FOR_USER));
                log.info("AccessUserFilter: " + userDto + " try to get url: " + url);
            }
        } else {
            String url = ((HttpServletRequest) servletRequest).getRequestURI();
            resp.sendRedirect(req.getContextPath().concat("/user/login"));
            log.info("AccessUserFilter someone try to get url: " + url);
        }
    }

    @Override
    public void destroy() {
        //empty method
    }
}
