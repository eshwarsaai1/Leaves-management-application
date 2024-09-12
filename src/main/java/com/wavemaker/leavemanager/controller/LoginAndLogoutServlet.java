package com.wavemaker.leavemanager.controller;

import com.wavemaker.leavemanager.models.User;
import com.wavemaker.leavemanager.util.CookieStore;
import com.wavemaker.leavemanager.services.impl.UserValidationServiceImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Map;
import java.util.UUID;

@WebServlet("/authentication")
public class LoginAndLogoutServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginAndLogoutServlet.class);

//    Creates a cookie as authCookie if login credentials are valid and set cookie in Response
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();

        try {
            User user = gson.fromJson(request.getReader(), User.class);

            if (user == null) {
                LOGGER.debug("user input is null");
                return;
            }

            int employeeId = UserValidationServiceImpl.getInstance().validateUser(user);

            if (employeeId == 0) {
                response.getWriter().println(gson.toJsonTree(Map.of("status", "fail")));
                LOGGER.info("Invalid user");
            }
            else {
                String authToken = UUID.randomUUID().toString();
                Cookie cookie = new Cookie("AuthCookie", authToken);
                CookieStore.add(authToken, employeeId);
                cookie.setPath("/");
                cookie.setSecure(false);
                cookie.setHttpOnly(true);
                cookie.setMaxAge(60 * 60 * 2);
                response.addCookie(cookie);
                response.getWriter().println(gson.toJsonTree(Map.of("status", "success")));
                LOGGER.info("User validation successful");
            }
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }


//    Deletes the Cookies in cookie store and set max age of Auth cookie to 0 in Response
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("AuthCookie")) {
                CookieStore.delete(cookie.getValue());
                cookie.setMaxAge(0);
                cookie.setPath("/");
                response.addCookie(cookie);
                LOGGER.info("user logged out");
            }
        }

    }
}

