package com.wavemaker.leavemanager.services.impl;

import com.wavemaker.leavemanager.models.LeaveRequest;
import com.wavemaker.leavemanager.repository.impl.LeaveRequestsRepositoryImpl;
import com.wavemaker.leavemanager.services.LeaveRequestsServices;

import java.util.List;

public class LeaveRequestsServicesImpl implements LeaveRequestsServices {
    private static LeaveRequestsServicesImpl leaveRequestsServices;

    private LeaveRequestsServicesImpl() {
        //
    }

    public static synchronized LeaveRequestsServicesImpl getInstance() {
        if (leaveRequestsServices == null) {
            leaveRequestsServices = new LeaveRequestsServicesImpl();
        }
        return leaveRequestsServices;
    }

    @Override
    public void applyLeaveRequest(LeaveRequest leave) {
        LeaveRequestsRepositoryImpl.getInstance().addLeaveRequest(leave);
    }

    @Override
    public void updateRequestStatus(int leaveId, String newStatus) {
        LeaveRequestsRepositoryImpl.getInstance().updateLeaveStatus(leaveId, newStatus);
    }

    @Override
    public List<LeaveRequest> getAllRequests(int managerId) {
        return LeaveRequestsRepositoryImpl.getInstance().getMyTeamLeaveRequests(managerId);
    }

    @Override
    public List<LeaveRequest> getAllLeaves(int employeeId) {
        return LeaveRequestsRepositoryImpl.getInstance().getMyLeaveRequests(employeeId);
    }

    @Override
    public int[] getLeavesCount(int employeeId) {
        return LeaveRequestsRepositoryImpl.getInstance().getLeavesCount(employeeId);
    }
}
