package com.wavemaker.leavemanager.controller;

import com.google.gson.Gson;
import com.wavemaker.leavemanager.services.impl.AnnualLeavesServicesImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@WebServlet(urlPatterns = "/annualLeaves")
public class GetAnnualLeavesServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetAnnualLeavesServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        try{
            Gson gson = new Gson();
            int[] annualLeaves = AnnualLeavesServicesImpl.getInstance().getAnnualLeaves();
            response.getWriter().println(gson.toJsonTree(Map.of("annualLeaves",annualLeaves)));
            LOGGER.info("Annual leaves are returned");
        }catch (Exception e){
            LOGGER.error("Exception: ", e);
        }
    }
}
