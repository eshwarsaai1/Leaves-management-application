package com.wavemaker.leavemanager.services;


import com.wavemaker.leavemanager.models.LeaveRequest;

import java.util.List;

public interface LeaveRequestsServices {
    void applyLeaveRequest(LeaveRequest leave);

    void updateRequestStatus(int leaveId, String newStatus);

    List<LeaveRequest> getAllRequests(int managerId);

    List<LeaveRequest> getAllLeaves(int employeeId);

    int[] getLeavesCount(int employeeId);
}
