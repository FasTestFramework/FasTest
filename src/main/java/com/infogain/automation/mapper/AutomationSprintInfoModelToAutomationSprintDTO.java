package com.infogain.automation.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.infogain.automation.dto.AutomationSprintDTO;
import com.infogain.automation.model.AutomationSprintInfoModel;

/**
 * Copyright (c) 2019 Infogain. All Rights Reserved.<br>
 * 
 * Theme - Automation<br>
 * Feature - Automation and Testing<br>
 * Description - This class is for mapping data from Peripheral Sprint Info Model of {@link AutomationSprintInfoModel}
 * to AutomationSprintDTO
 * 
 * @author Rudhra Koul [103264]
 * @version 1.0.0
 * @since Dec 12, 2019
 */
public class AutomationSprintInfoModelToAutomationSprintDTO {
    private static final Logger logger = LogManager.getLogger(AutomationSprintInfoModelToAutomationSprintDTO.class);

    /**
     * This method maps the data from {@link AutomationSprintInfoModel} to {@link AutomationSprintDTO}
     * 
     * @param modelList - list of {@link AutomationSprintInfoModel}
     * @return list of {@link AutomationSprintDTO}
     * @since Dec 13, 2019
     */
    public static List<AutomationSprintDTO> convertAutomationSprintInfoModelToSprintDto(
                    List<AutomationSprintInfoModel> modelList) {

        logger.traceEntry(
                        "convertAutomationSprintInfoModelToSprintDto method of AutomationSprintInfoModelToAutomationSprintDTO class");
        List<AutomationSprintDTO> dtoList = new ArrayList<AutomationSprintDTO>();

        modelList.forEach(model -> {
            dtoList.add(new AutomationSprintDTO(model.getPsi().getPi(), model.getPsi().getSprint(),
                            model.getProjectName(), model.getSprintStartDate(), model.getSprintEndDate()));

        });

        return logger.traceExit(dtoList);
    }
}
