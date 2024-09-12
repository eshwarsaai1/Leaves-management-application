package com.wavemaker.leavemanager.services;


import com.wavemaker.leavemanager.models.dto.ManagerViewDTO;
import com.wavemaker.leavemanager.models.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeServices {
    EmployeeDTO getEmployee(int employeeId);

    List<ManagerViewDTO> getMyTeam(int employeeId);
}
