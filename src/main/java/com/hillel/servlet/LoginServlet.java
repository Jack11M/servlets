package com.hillel.servlet;

import com.hillel.dto.UserDto;
import com.hillel.mapper.UserMapperImpl;
import com.hillel.model.Status;
import com.hillel.model.User;
import com.hillel.service.UserService;
import com.hillel.util.StringConstant;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/login")
@Log4j
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute(StringConstant.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LoginServlet's doGet() called");

        req.getRequestDispatcher("../WEB-INF/jsp/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("LoginServlet's doPost() called");

        String userName = req.getParameter(StringConstant.USERNAME);
        String password = req.getParameter(StringConstant.PASSWORD);

        if (!password.isEmpty() && !userName.isEmpty()) {
            if (userService.userExistsByNameAndPass(userName, password)) {
                User user = userService.getUserByUsername(userName);
                req.setAttribute(StringConstant.USER, user);
                doPut(req, resp);
            } else {
                resp.sendError(404, StringConstant.NOT_FOUND.concat(" : ").concat(StringConstant.UNREGISTERED_USER));
                log.error("Error in LoginServlet (doPost) - 404 Not Found : unregistered user");
            }
        } else {
            resp.sendError(400, StringConstant.BAD_REQUEST);
            log.error("Error in LoginServlet (doPost) - 400 BAD REQUEST : user was not logged in");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("LoginServlet's doPut() called");
        User user = (User) req.getAttribute(StringConstant.USER);
        user.setStatus(Status.LOGGED_IN);
        UserDto userDto = new UserMapperImpl().userToUserDto(user);
        log.info("LoginServlet's doPut() user was logged in: " + userDto);
        HttpSession session = req.getSession();
        session.setAttribute(StringConstant.USER_DTO, userDto);
        resp.sendRedirect(req.getContextPath().concat("/user/home"));
    }
}
