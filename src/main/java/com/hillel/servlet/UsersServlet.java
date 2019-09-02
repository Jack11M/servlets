package com.hillel.servlet;

import com.hillel.model.User;
import com.hillel.service.UserService;
import com.hillel.util.JsonUtil;
import com.hillel.util.StringConstant;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/management/users")
@Log4j
public class UsersServlet extends HttpServlet {
    private JsonUtil jsonUtil;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute(StringConstant.USER_SERVICE);
        jsonUtil = (JsonUtil) getServletContext().getAttribute(StringConstant.JSON_UTIL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UsersServlet's doGet() called");
        List<User> users = userService.getUsers();
        if (users != null) {
            try (PrintWriter out = resp.getWriter()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(jsonUtil.jsonWriterFromListOfUsers(users));
            }
        } else {
            resp.sendError(404, StringConstant.NOT_FOUND);
            log.error("Error in UsersServlet (doGet) - 404 NOT FOUND : List of users is empty");
        }
    }
}
