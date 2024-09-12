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

@WebServlet(urlPatterns = "/myLeaves")
public class MyLeavesServlet extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(MyLeavesServlet.class);


//    Adds the leave request to DB
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("application/json");
        Gson gson = new Gson();

        try {
            LeaveRequest leaveRequest = gson.fromJson(request.getReader(), LeaveRequest.class);
            leaveRequest.setEmployeeId((int) request.getAttribute("employeeId"));
            LeaveRequestsServicesImpl.getInstance().applyLeaveRequest(leaveRequest);
            LOGGER.info("Received Leave request");
        } catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }

//    Returns all the leaves taken by User as JSON in Response
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        try {
            Gson gson = new Gson();
            List<LeaveRequest> leaveRequestList =
                    LeaveRequestsServicesImpl.getInstance().getAllLeaves((int) request.getAttribute("employeeId"));

            response.getWriter().println(gson.toJsonTree(Map.of("leaves", leaveRequestList)));
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }
}
