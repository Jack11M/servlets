package com.hillel.servlet;

import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/user/success")
@Log4j
public class SuccessLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("SuccessLoginServlet's doGet() called");

        req.getRequestDispatcher("../WEB-INF/jsp/successPage.jsp").forward(req, resp);
    }
}
