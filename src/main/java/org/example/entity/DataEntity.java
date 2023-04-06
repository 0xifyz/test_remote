package org.example.entity;

import lombok.Data;

import java.util.List;

/**
 * program: data-migrate
 * <p>
 * 功能描述:
 *
 * @author : YuanZ
 * @since : 2023-03-09 11:26
 **/
@Data
public class DataEntity {
    List<ProjectEntity> projectEntityList;
    List<ProjectAirConditionerTechnologyEvaluationEntity> technologyEvaluationEntityList;
    List<ProjectAirConditionerBusinessEvaluationEntity> businessEvaluationEntities;
    List<ProjectAirConditionerEntity> projectAirConditionerEntityList;
}
