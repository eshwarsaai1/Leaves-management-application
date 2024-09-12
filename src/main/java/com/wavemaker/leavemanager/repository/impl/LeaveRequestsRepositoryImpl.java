package com.wavemaker.leavemanager.repository.impl;

import com.wavemaker.leavemanager.models.dto.LeaveRequestDTO;
import com.wavemaker.leavemanager.models.LeaveRequest;
import com.wavemaker.leavemanager.repository.LeaveRequestRepository;
import com.wavemaker.leavemanager.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;

public class LeaveRequestsRepositoryImpl implements LeaveRequestRepository {
    private static final String GET_MY_TEAM_LEAVE_REQUESTS = "SELECT * FROM LEAVES L JOIN " +
            "(SELECT EMPLOYEE_ID, EMPLOYEE_NAME, EMAIL FROM EMPLOYEE WHERE MANAGER_ID = ?) E " +
            "WHERE E.EMPLOYEE_ID = L.EMPLOYEE_ID";

    private static final String LEAVES_TAKEN_COUNT = "SELECT SUM(DAYS) FROM LEAVES WHERE EMPLOYEE_ID = ?" +
            " AND LEAVE_TYPE_ID = ? AND STATUS = 'APPROVED'";

    private static final String GET_MY_LEAVE_REQUESTS = "SELECT * FROM LEAVES WHERE EMPLOYEE_ID = ? " +
            "ORDER BY APPLY_DATE";

    private static final String ADD_LEAVE_REQUEST = "INSERT INTO LEAVES " +
            "(EMPLOYEE_ID, LEAVE_TYPE_ID , FROM_DATE, TO_DATE, DESCRIPTION, APPLY_DATE, DAYS) " +
            "VALUES (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE_LEAVE_STATUS = "UPDATE LEAVES SET STATUS = ? WHERE LEAVE_ID = ?";

    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveRequestsRepositoryImpl.class);

    private static LeaveRequestsRepositoryImpl leaveRequestsRepositoryImpl;
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;

    private LeaveRequestsRepositoryImpl() {
        //
    }

    public static synchronized LeaveRequestsRepositoryImpl getInstance() {
        if (leaveRequestsRepositoryImpl == null) {
            leaveRequestsRepositoryImpl = new LeaveRequestsRepositoryImpl();
        }
        return leaveRequestsRepositoryImpl;
    }


//    Adds the Leave request to the Database by taking LeaveRequest object as parameter
    @Override
    public void addLeaveRequest(LeaveRequest leave) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(ADD_LEAVE_REQUEST);
            preparedStatement.setInt(1, leave.employeeId);
            preparedStatement.setString(2, leave.leaveTypeId);
            preparedStatement.setDate(3, Date.valueOf(leave.fromDate));
            preparedStatement.setDate(4, Date.valueOf(leave.toDate));
            preparedStatement.setString(5, leave.description);
            preparedStatement.setDate(6, Date.valueOf(leave.applyDate));
            preparedStatement.setInt(7, leave.days);
            preparedStatement.executeUpdate();

            LOGGER.info("Add leave request");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }

//    Updates the Status of leave request to Accept or Reject by taking LEave Id and new Status String as parameters
    @Override
    public void updateLeaveStatus(int leaveId, String newStatus) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(UPDATE_LEAVE_STATUS);
            preparedStatement.setInt(2, leaveId);
            preparedStatement.setString(1, newStatus);
            LOGGER.info("{}", newStatus);
            preparedStatement.executeUpdate();
            LOGGER.info("Leave updated");
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
    }

//    Returns all the leaves of employees under manager as ArrayList of leaveRequests by taking the employee Id of manager as parameter
    @Override
    public ArrayList<LeaveRequest> getMyTeamLeaveRequests(int managerId) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_MY_TEAM_LEAVE_REQUESTS);
            preparedStatement.setInt(1, managerId);
            resultSet = preparedStatement.executeQuery();
            ArrayList<LeaveRequest> leaveRequestList = new ArrayList<>();

            while (resultSet.next()) {
                LeaveRequestDTO leave = new LeaveRequestDTO();
                leave.leaveId = resultSet.getInt("leave_id");
                leave.employeeId = resultSet.getInt("employee_id");
                leave.leaveTypeId = resultSet.getString("leave_type_id");
                leave.fromDate = resultSet.getDate("from_date").toString();
                leave.toDate = resultSet.getDate("to_date").toString();
                leave.description = resultSet.getString("Description");
                leave.applyDate = resultSet.getDate("APPLY_DATE").toString();
                leave.status = resultSet.getString("status");
                leave.days = resultSet.getInt("DAYS");
                leave.employeeName = resultSet.getString("EMPLOYEE_NAME");
                leave.employeeEmail = resultSet.getString("EMAIL");
                leaveRequestList.add(leave);
            }
            resultSet.close();
            LOGGER.info("my team Leaves returned");
            return leaveRequestList;
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }
        return null;
    }


//    Returns all the leaves of employee as ArrayList of leaveRequests by taking the employee Id as parameter
    @Override
    public ArrayList<LeaveRequest> getMyLeaveRequests(int employeeId) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_MY_LEAVE_REQUESTS);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();
            ArrayList<LeaveRequest> leaveRequestList = new ArrayList<>();

            while (resultSet.next()) {
                LeaveRequest leave = new LeaveRequest();
                leave.leaveId = resultSet.getInt("leave_id");
                leave.employeeId = resultSet.getInt("employee_id");
                leave.leaveTypeId = resultSet.getString("leave_type_id");
                leave.fromDate = resultSet.getDate("from_date").toString();
                leave.toDate = resultSet.getDate("to_date").toString();
                leave.description = resultSet.getString(("Description"));
                leave.applyDate = resultSet.getDate("APPLY_DATE").toString();
                leave.status = resultSet.getString("status");
                leave.days = resultSet.getInt("days");
                leaveRequestList.add(leave);
            }

            resultSet.close();
            LOGGER.info("my Leaves returned");
            return leaveRequestList;
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }

        return null;
    }

//    Returns leaves taken count of each leave type as array of an employee by taking employee Id as parameter
    @Override
    public int[] getLeavesCount(int employeeId) {
        int[] leavesCount = new int[5];

        try {
            PreparedStatement preparedStatement =
                    DatabaseConnector.getInstance().prepareStatement(LEAVES_TAKEN_COUNT);

            preparedStatement.setInt(1, employeeId);
            preparedStatement.setInt(2, 1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leavesCount[1] = resultSet.getInt(1);
            }
            resultSet.close();

            preparedStatement.setInt(2, 2);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leavesCount[2] = resultSet.getInt(1);
            }
            resultSet.close();

            preparedStatement.setInt(2, 3);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leavesCount[3] = resultSet.getInt(1);
            }
            resultSet.close();

            preparedStatement.setInt(2, 4);
            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                leavesCount[4] = resultSet.getInt(1);
            }

            resultSet.close();
            LOGGER.info("Array of leaves taken count returned");
            return leavesCount;
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }

        return leavesCount;
    }
}
