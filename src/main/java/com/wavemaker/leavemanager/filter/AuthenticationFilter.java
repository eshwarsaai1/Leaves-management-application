package com.wavemaker.leavemanager.filter;

import com.wavemaker.leavemanager.util.CookieStore;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@WebFilter(urlPatterns = {"/dashboard.html", "/myLeaves", "/employee", "/teamLeaves", "/getMyTeam"})
public class AuthenticationFilter implements Filter {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain){
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        Cookie[] cookies = httpServletRequest.getCookies();
        if (cookies != null) {
            LOGGER.info("Cookies collected");
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("AuthCookie")) {
                    if (CookieStore.has(cookie.getValue())) {
                        LOGGER.info("User Authorization is Valid");
                        httpServletRequest.setAttribute("employeeId", CookieStore.get(cookie.getValue()));
                        try {
                            filterChain.doFilter(httpServletRequest, httpServletResponse);
                        } catch (Exception e) {
                            LOGGER.error("Exception: ", e);
                        }
                        return;
                    }
                }
            }
        }

        LOGGER.debug("User Not Authorized");
        try {
            httpServletResponse.sendRedirect(httpServletRequest.getContextPath());
        } catch (IOException e) {
            LOGGER.error("Exception: ", e);
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
