package com.wavemaker.leavemanager.controller;

import com.google.gson.Gson;
import com.wavemaker.leavemanager.models.dto.EmployeeDTO;
import com.wavemaker.leavemanager.services.impl.EmployeeServicesImpl;
import com.wavemaker.leavemanager.services.impl.LeaveRequestsServicesImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Map;


@WebServlet(urlPatterns = "/employee")
public class EmployeeServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServlet.class);


//    Returns employee details and all the leaves taken as JSON in RESPONSE
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            Gson gson = new Gson();
            EmployeeDTO employeeDTO = EmployeeServicesImpl.getInstance().getEmployee((int) request.getAttribute("employeeId"));

            LOGGER.info("Employee details are fetched");
            int[] leavesSummary = LeaveRequestsServicesImpl.getInstance().getLeavesCount((int) request.getAttribute("employeeId"));
            LOGGER.info("Employee leaves are fetched");

            response.getWriter().println(gson.toJsonTree(Map.of("employee", employeeDTO, "leaves", leavesSummary)));
            LOGGER.info("EmployeeDTO and leaves list is returned");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }
}
