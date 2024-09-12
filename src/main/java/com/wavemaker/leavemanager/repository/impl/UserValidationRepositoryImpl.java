package com.wavemaker.leavemanager.repository.impl;

import com.wavemaker.leavemanager.models.User;
import com.wavemaker.leavemanager.repository.UserValidationRepository;
import com.wavemaker.leavemanager.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

public class UserValidationRepositoryImpl implements UserValidationRepository {
    private static final String VALIDATE_USER_CREDENTIALS = "SELECT EMAIL FROM EMPLOYEE_CREDENTIALS " +
            "WHERE EMAIL = BINARY ? AND PASSWORD = BINARY ?";

    private static final String GET_EMPLOYEE_ID_BY_EMAIL = "SELECT EMPLOYEE_ID FROM EMPLOYEE WHERE EMAIL = ?";


    private static final Logger logger = LoggerFactory.getLogger(UserValidationRepositoryImpl.class);
    private static PreparedStatement preparedStatement;
    private static ResultSet resultSet;
    private static UserValidationRepositoryImpl userValidationRepositoryImpl;

    private UserValidationRepositoryImpl() {
        //Making class unable to instantiate more than once
    }

    public static synchronized UserValidationRepositoryImpl getInstance() {
        if (userValidationRepositoryImpl == null) {
            userValidationRepositoryImpl = new UserValidationRepositoryImpl();
        }
        return userValidationRepositoryImpl;
    }


//    Returns employee Id by taking employee Email as parameter
    @Override
    public int getEmployeeId(String email) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_EMPLOYEE_ID_BY_EMAIL);
            preparedStatement.setString(1, email);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("Got employee_id");
                return resultSet.getInt("EMPLOYEE_ID");
            }

            resultSet.close();
            logger.info("Employee not found");
        }
        catch (Exception e) {
            logger.error("exception", e);
        }

        return 0;
    }


//    Returns employeeID of Employee if the User details matches with any of the user details in DB else returns 0
    @Override
    public int validateUser(User user) {
        try {
            preparedStatement = DatabaseConnector.getInstance().prepareStatement(VALIDATE_USER_CREDENTIALS);
            preparedStatement.setString(1, user.getEmail());
            preparedStatement.setString(2, user.getPassword());
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                logger.info("User validated successfully");
                return getEmployeeId(resultSet.getString("EMAIL"));
            }

            resultSet.close();
            logger.info("Incorrect username or PASSWORD");
        }
        catch (Exception e) {
            logger.error("exception", e);
        }
        return 0;
    }
}