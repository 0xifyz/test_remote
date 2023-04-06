package org.example;

import org.example.util.ProjectDataTransferUtil;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
     //   ProjectDataTransferUtil.insertPartnerList();
        ProjectDataTransferUtil.insertProjectEntity();
        ProjectDataTransferUtil.insertProjectAirCondition();
        ProjectDataTransferUtil.insertProjectAirConditionBusinessEval();
        ProjectDataTransferUtil.insertProjectAirConditionTechnologyEval();
    }
}