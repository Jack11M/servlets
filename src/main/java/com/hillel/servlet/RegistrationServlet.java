package com.hillel.servlet;

import com.hillel.model.User;
import com.hillel.service.UserService;
import com.hillel.util.StringConstant;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/register")
@Log4j
public class RegistrationServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute(StringConstant.USER_SERVICE);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("RegistrationServlet's doGet() called");

        req.getRequestDispatcher("../WEB-INF/jsp/registration.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("RegistrationServlet's doPost() called");

        String username = req.getParameter(StringConstant.USERNAME);
        String password = req.getParameter(StringConstant.PASSWORD);
        String firstName = req.getParameter(StringConstant.FIRST_NAME);
        String lastName = req.getParameter(StringConstant.LAST_NAME);

        if (!password.isEmpty() && !username.isEmpty() && !firstName.isEmpty()) {
            if (!userService.usernameExists(username)) {
                User user = new User(firstName, username, password);
                if (!lastName.isEmpty()) {
                    user.setLastName(lastName);
                }
                if (userService.createUser(user)) {
                    log.info("User has been created: " + user);
                    req.setAttribute(StringConstant.USER, user);
                    resp.sendRedirect(req.getContextPath().concat("/user/login"));
                } else {
                    resp.sendError(400, StringConstant.BAD_REQUEST.concat(" : ").concat(StringConstant.USERNAME_EXISTS));
                    log.error("Error in RegistrationServlet (doPost) - 400 BAD REQUEST : username already exists");
                }
            } else {
                resp.sendError(400, StringConstant.BAD_REQUEST.concat(" : ").concat(StringConstant.USERNAME_EXISTS));
                log.error("Error in RegistrationServlet (doPost) - 400 BAD REQUEST : username already exists");
            }
        } else {
            resp.sendError(400, StringConstant.BAD_REQUEST);
            log.error("Error in RegistrationServlet (doPost) - 400 BAD REQUEST : user was not registered");
        }
    }
}
