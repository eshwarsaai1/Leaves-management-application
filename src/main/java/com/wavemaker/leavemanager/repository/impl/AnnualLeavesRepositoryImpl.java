package com.wavemaker.leavemanager.repository.impl;

import com.wavemaker.leavemanager.repository.AnnualLeavesRepository;
import com.wavemaker.leavemanager.util.DatabaseConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AnnualLeavesRepositoryImpl implements AnnualLeavesRepository {
    private static final String GET_ANNUAL_LEAVES = " SELECT TOTAL_ALLOWED FROM LEAVE_TYPE WHERE LEAVE_TYPE_ID = ?";


    private static final Logger LOGGER = LoggerFactory.getLogger(AnnualLeavesRepositoryImpl.class);

    private static AnnualLeavesRepositoryImpl annualLeavesRepositoryImpl;

    private AnnualLeavesRepositoryImpl(){
        //
    }

    public static synchronized AnnualLeavesRepositoryImpl getInstance(){
        if(annualLeavesRepositoryImpl == null){
            annualLeavesRepositoryImpl = new AnnualLeavesRepositoryImpl();
        }
        return annualLeavesRepositoryImpl;
    }

    @Override
    public int[] getAnnualLeaves() {
        int[] annualLeaves = new int[5];
        try {
            PreparedStatement preparedStatement = DatabaseConnector.getInstance().prepareStatement(GET_ANNUAL_LEAVES);
            preparedStatement.setInt(1,1);
            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                annualLeaves[1] = resultSet.getInt(1);
            }
            preparedStatement.setInt(1,2);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                annualLeaves[2] = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.setInt(1,3);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                annualLeaves[3] = resultSet.getInt(1);
            }
            resultSet.close();
            preparedStatement.setInt(1,4);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()){
                annualLeaves[4] = resultSet.getInt(1);
            }
            resultSet.close();
            LOGGER.info("annual leaves are fetched");
        }
        catch (Exception e){
            LOGGER.error("Exception: ", e);
        }
        return annualLeaves;
    }
}
