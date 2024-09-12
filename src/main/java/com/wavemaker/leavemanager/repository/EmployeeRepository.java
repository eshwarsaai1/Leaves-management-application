package com.wavemaker.leavemanager.repository;


import com.wavemaker.leavemanager.models.dto.ManagerViewDTO;
import com.wavemaker.leavemanager.models.dto.EmployeeDTO;

import java.util.List;

public interface EmployeeRepository {
    EmployeeDTO getEmployee(int employeeId);

    List<ManagerViewDTO> getMyTeam(int employeeId);
}
