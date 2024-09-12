package com.wavemaker.leavemanager.controller;

import com.wavemaker.leavemanager.models.LeaveRequest;
import com.wavemaker.leavemanager.services.impl.LeaveRequestsServicesImpl;
import com.google.gson.Gson;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = "/teamLeaves")
public class TeamLeavesServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(TeamLeavesServlet.class);


//    Returns the all the leave requests of every employee under user in JSON in Response
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            Gson gson = new Gson();
            List<LeaveRequest> leaveRequestList =
                    LeaveRequestsServicesImpl.getInstance().getAllRequests((int) request.getAttribute("employeeId"));

            response.getWriter().println(gson.toJsonTree(Map.of("leaves", leaveRequestList)));
            LOGGER.info("Leave Requests returned");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }


//    Updates the user action on leave request of employee under him/her
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");

        try {
            int leaveId = Integer.parseInt(request.getParameter("leaveId"));
            String newStatus = request.getParameter("newStatus");
            LOGGER.info("got new status");
            LeaveRequestsServicesImpl.getInstance().updateRequestStatus(leaveId, newStatus);
            LOGGER.info("leave status updated");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }
}
