package com.wavemaker.leavemanager.services.impl;

import com.wavemaker.leavemanager.repository.impl.AnnualLeavesRepositoryImpl;
import com.wavemaker.leavemanager.services.AnnualLeavesServices;

public class AnnualLeavesServicesImpl implements AnnualLeavesServices {
    private static AnnualLeavesServicesImpl annualLeavesServicesImpl;

    private AnnualLeavesServicesImpl(){
        //
    }

    public static synchronized AnnualLeavesServicesImpl getInstance(){
        if(annualLeavesServicesImpl == null){
            annualLeavesServicesImpl = new AnnualLeavesServicesImpl();
        }
        return annualLeavesServicesImpl;
    }

    @Override
    public int[] getAnnualLeaves() {
        return AnnualLeavesRepositoryImpl.getInstance().getAnnualLeaves();
    }
}
