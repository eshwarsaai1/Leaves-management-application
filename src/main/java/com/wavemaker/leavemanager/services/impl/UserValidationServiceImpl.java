package com.wavemaker.leavemanager.services.impl;

import com.wavemaker.leavemanager.models.User;
import com.wavemaker.leavemanager.repository.impl.UserValidationRepositoryImpl;
import com.wavemaker.leavemanager.services.UserValidationServices;

public class UserValidationServiceImpl implements UserValidationServices {
    private static UserValidationServiceImpl userValidationServiceImpl;

    private UserValidationServiceImpl() {
        //
    }

    public static synchronized UserValidationServiceImpl getInstance() {

        if (userValidationServiceImpl == null) {
            userValidationServiceImpl = new UserValidationServiceImpl();
        }

        return userValidationServiceImpl;
    }

    public int validateUser(User user) {
        return UserValidationRepositoryImpl.getInstance().validateUser(user);
    }
}