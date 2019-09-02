package com.hillel.servlet;

import com.hillel.dto.DeleteUserDto;
import com.hillel.dto.UpdateUserDto;
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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

@WebServlet("/management/user")
@Log4j
public class UserServlet extends HttpServlet {
    private UserService userService;
    private JsonUtil jsonUtil;

    @Override
    public void init() throws ServletException {
        super.init();
        userService = (UserService) getServletContext().getAttribute(StringConstant.USER_SERVICE);
        jsonUtil = (JsonUtil) getServletContext().getAttribute(StringConstant.JSON_UTIL);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet's doGet() called");

        String usernameFromParameters = req.getParameter(StringConstant.USERNAME);
        User user = userService.getUserByUsername(usernameFromParameters);
        if (user != null) {
            try (PrintWriter out = resp.getWriter()) {
                resp.setContentType("application/json");
                resp.setCharacterEncoding("UTF-8");
                out.print(jsonUtil.jsonWriterFromUser(user));
            }
        } else {
            resp.sendError(404, StringConstant.NOT_FOUND.concat(" : ").concat(StringConstant.UNREGISTERED_USER));
            log.error("Error in UserServlet (doGet) - 404 NOT FOUND : unregistered user");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet's doPost() called");

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        User newUser = jsonUtil.jsonReaderInUser(br.readLine());
        if (newUser != null && newUser.getUsername() != null && newUser.getPassword() != null && newUser.getFirstName() != null
                && !userService.usernameExists(newUser.getUsername())) {
            if (!newUser.getLastName().isEmpty()) {
                newUser.setLastName(newUser.getLastName());
            }
            if (userService.createUser(newUser)) {
                log.info("User has been created: " + newUser);
                req.setAttribute(StringConstant.USER, newUser);
                resp.sendRedirect(req.getContextPath());
            } else {
                resp.sendError(400, StringConstant.BAD_REQUEST.concat(" : ").concat(StringConstant.USERNAME_EXISTS));
                log.error("Error in UserServlet (doPost) - 400 BAD REQUEST : username already exists");
            }
        } else {
            resp.sendError(400, StringConstant.BAD_REQUEST);
            log.error("Error in UserServlet (doPost) - 400 BAD REQUEST : newUser has not been created");
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet's doPut() called");

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        UpdateUserDto updateUserDto = jsonUtil.jsonReaderInUpdateUserDto(br.readLine());

        if (updateUserDto != null) {
            if (userService.updateUser(updateUserDto)) {
                log.info("User with username has been updated: " + req.getParameter(StringConstant.USERNAME));
            } else {
                resp.sendError(400, StringConstant.BAD_REQUEST);
                log.error("Error in UserServlet (doPut) - 400 BAD REQUEST : user has not been updated");
            }
        } else {
            resp.sendError(404, StringConstant.NOT_FOUND.concat(" : ").concat(StringConstant.UNREGISTERED_USER));
            log.error("Error in UserServlet (doPut) - 404 NOT FOUND : unregistered user");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("UserServlet's doDelete() called");

        BufferedReader br = new BufferedReader(new InputStreamReader(req.getInputStream()));
        DeleteUserDto deleteUserDto = jsonUtil.jsonReaderInDeleteUserDto(br.readLine());

        if (deleteUserDto != null && userService.deleteUser(deleteUserDto.getUsername())) {
            log.info("User with username has been deleted: " + deleteUserDto);
        } else {
            resp.sendError(404, StringConstant.NOT_FOUND.concat(" : ").concat(StringConstant.UNREGISTERED_USER));
            log.error("Error in UserServlet (doDelete) - 404 NOT FOUND : unregistered user");
        }
    }
}
