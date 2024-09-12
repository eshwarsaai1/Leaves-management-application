package com.wavemaker.leavemanager.repository.impl;

import com.wavemaker.leavemanager.models.dto.ManagerViewDTO;
import com.wavemaker.leavemanager.models.dto.EmployeeDTO;
import com.wavemaker.leavemanager.repository.EmployeeRepository;
import com.wavemaker.leavemanager.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class EmployeeRepositoryImpl implements EmployeeRepository {

    private static final String GET_MY_MANAGER = "SELECT EMPLOYEE_NAME , EMAIL FROM EMPLOYEE WHERE EMPLOYEE_ID = " +
            "(SELECT MANAGER_ID FROM EMPLOYEE WHERE EMPLOYEE_ID = ? )";

    private static final String GET_MY_TEAM_EMPLOYEES = "SELECT * FROM EMPLOYEE WHERE MANAGER_ID = ?";

    private static final String GET_EMPLOYEE = "SELECT * FROM EMPLOYEE WHERE EMPLOYEE_ID = ?";


    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeRepositoryImpl.class);
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static EmployeeRepositoryImpl employeeRepositoryImpl;

    private EmployeeRepositoryImpl() {
        //
    }

    public static synchronized EmployeeRepositoryImpl getInstance() {
        if (employeeRepositoryImpl == null) {
            employeeRepositoryImpl = new EmployeeRepositoryImpl();
        }
        return employeeRepositoryImpl;
    }


//    Returns employee details and role of employee as EmployeeDTO object by taking employee Id as parameter
    @Override
    public EmployeeDTO getEmployee(int employeeId) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_EMPLOYEE);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeeDTO.employeeId = resultSet.getInt("EMPLOYEE_ID");
                employeeDTO.employeeName = resultSet.getString("EMPLOYEE_NAME");
                employeeDTO.email = resultSet.getString("EMAIL");
                employeeDTO.phone = resultSet.getString("PHONE");
                employeeDTO.dateOfBirth = resultSet.getDate("DATE_OF_BIRTH");
                employeeDTO.gender = resultSet.getString("GENDER");
            }

            LOGGER.info("Employee fetched");
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_MY_TEAM_EMPLOYEES);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeeDTO.role = "Manager";
            }
            else {
                employeeDTO.role = "Employee";
            }

            LOGGER.info("role fetched");
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_MY_MANAGER);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                employeeDTO.managerName = resultSet.getString(1);
                employeeDTO.managerEmail = resultSet.getString(2);
            }
            resultSet.close();
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }

        LOGGER.info("EmployeeDTO returned");
        return employeeDTO;
    }


//    Returns every employee details under the Manager by taking employee Id of manager as parameter
    @Override
    public List<ManagerViewDTO> getMyTeam(int employeeId) {
        List<ManagerViewDTO> teamList = new ArrayList<>();
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_MY_TEAM_EMPLOYEES);
            preparedStatement.setInt(1, employeeId);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                ManagerViewDTO managerViewDTO = new ManagerViewDTO();
                managerViewDTO.employeeId = resultSet.getInt("EMPLOYEE_ID");
                managerViewDTO.employeeName = resultSet.getString("EMPLOYEE_NAME");
                managerViewDTO.email = resultSet.getString("EMAIL");
                managerViewDTO.phone = resultSet.getString("PHONE");
                managerViewDTO.dateOfBirth = resultSet.getDate("DATE_OF_BIRTH");
                managerViewDTO.gender = resultSet.getString("GENDER");
                teamList.add(managerViewDTO);
            }

            resultSet.close();
            LOGGER.info("Team employees returned");
            return teamList;
        }
        catch (Exception e) {
            LOGGER.error("Exception: ", e);
        }

        return teamList;
    }
}
