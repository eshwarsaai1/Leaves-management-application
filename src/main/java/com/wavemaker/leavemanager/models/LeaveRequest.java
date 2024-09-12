package com.wavemaker.leavemanager.models;

public class LeaveRequest {
    public int leaveId;
    public int employeeId;
    public String leaveTypeId;
    public String fromDate;
    public String toDate;
    public String applyDate;
    public String description;
    public String status = "pending";
    public int days;

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }
}
