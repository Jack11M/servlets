package com.hillel.listener;

import com.hillel.service.UserService;
import com.hillel.service.impl.UserServiceImpl;
import com.hillel.storage.UserStorage;
import com.hillel.util.*;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        UserService userService = new UserServiceImpl(new UserStorage());
        JsonUtil jsonUtil = new JsonUtil();

        ServletContext ctx = sce.getServletContext();
        ctx.setAttribute(StringConstant.USER_SERVICE, userService);
        ctx.setAttribute(StringConstant.JSON_UTIL, jsonUtil);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //empty method
    }
}
