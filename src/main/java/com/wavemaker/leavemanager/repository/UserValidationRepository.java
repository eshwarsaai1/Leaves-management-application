package com.wavemaker.leavemanager.repository;

import com.wavemaker.leavemanager.models.User;

public interface UserValidationRepository {
    int getEmployeeId(String email);

    int validateUser(User user);
}
