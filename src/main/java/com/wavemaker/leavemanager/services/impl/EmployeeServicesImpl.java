package com.wavemaker.leavemanager.services.impl;

import com.wavemaker.leavemanager.models.dto.ManagerViewDTO;
import com.wavemaker.leavemanager.models.dto.EmployeeDTO;
import com.wavemaker.leavemanager.repository.impl.EmployeeRepositoryImpl;
import com.wavemaker.leavemanager.services.EmployeeServices;

import java.util.List;

public class EmployeeServicesImpl implements EmployeeServices {

    private static EmployeeServicesImpl employeeServiceImpl;

    private EmployeeServicesImpl() {

    }

    public static synchronized EmployeeServicesImpl getInstance() {
        if (employeeServiceImpl == null) {
            employeeServiceImpl = new EmployeeServicesImpl();
        }
        return employeeServiceImpl;
    }

    @Override
    public EmployeeDTO getEmployee(int employeeId) {
        return EmployeeRepositoryImpl.getInstance().getEmployee(employeeId);
    }

    @Override
    public List<ManagerViewDTO> getMyTeam(int employeeId) {
        return EmployeeRepositoryImpl.getInstance().getMyTeam(employeeId);
    }
}
