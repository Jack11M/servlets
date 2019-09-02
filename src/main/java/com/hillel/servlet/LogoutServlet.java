package com.hillel.servlet;

import com.hillel.util.StringConstant;
import lombok.extern.log4j.Log4j;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/user/logout")
@Log4j
public class LogoutServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LogoutServlet's doGet() called");

        req.getRequestDispatcher("../WEB-INF/jsp/logout.jsp").forward(req, resp);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        log.info("LogoutServlet's doPost() called");

        String answerValue = req.getParameter("logout");
        if (answerValue.equalsIgnoreCase(StringConstant.YES)) {
            doPut(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath().concat("/user/home"));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("LogoutServlet's doPut() called");

        HttpSession session = req.getSession();
        session.invalidate();
        resp.sendRedirect("../index.jsp");
    }
}
