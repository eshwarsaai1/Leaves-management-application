package com.wavemaker.leavemanager.repository;


import com.wavemaker.leavemanager.models.LeaveRequest;

import java.util.ArrayList;

public interface LeaveRequestRepository {
    void addLeaveRequest(LeaveRequest leave);

    void updateLeaveStatus(int leaveId, String newStatus);

    ArrayList<LeaveRequest> getMyTeamLeaveRequests(int managerId);

    ArrayList<LeaveRequest> getMyLeaveRequests(int employeeId);

    int[] getLeavesCount(int employeeId);
}
