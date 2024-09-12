package com.wavemaker.leavemanager.controller;

import com.google.gson.Gson;
import com.wavemaker.leavemanager.models.dto.ManagerViewDTO;
import com.wavemaker.leavemanager.services.impl.EmployeeServicesImpl;
import com.wavemaker.leavemanager.services.impl.LeaveRequestsServicesImpl;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/getMyTeam")
public class GetMyTeamServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(GetMyTeamServlet.class);

//    Returns details and leaves taken array of every employee under manager as JSON in RESPONSE
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        Gson gson = new Gson();
        List<ManagerViewDTO> myTeam = EmployeeServicesImpl.getInstance().getMyTeam((int) request.getAttribute("employeeId"));

        LOGGER.info("My team fetched");

        for (ManagerViewDTO currentEmployee : myTeam) {
            currentEmployee.leaveSummary = LeaveRequestsServicesImpl.getInstance().getLeavesCount(currentEmployee.employeeId);
        }

        LOGGER.info("My team leave summary fetched");

        try {
            response.getWriter().println(gson.toJsonTree(Map.of("myTeam", myTeam)));
            LOGGER.info("My team employees are returned");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }
}
