package com.infogain.automation.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.infogain.automation.model.AutomationSprintId;
import com.infogain.automation.model.AutomationSprintInfoModel;

@Repository
public interface AutomationSprintRepository extends CrudRepository<AutomationSprintInfoModel, AutomationSprintId> {

    public List<AutomationSprintInfoModel> findAll();

}
