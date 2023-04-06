package org.example.entity;

import com.sun.org.apache.xpath.internal.operations.Mod;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 空调节能项目商务评估表
 * </p>
 *
 * @author yuanzhan
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectAirConditionerBusinessEvaluationEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer projectId;

    private Integer projectChannelId;

    private String chargeAccountLevel;

    private String dockingPerson;

    private String relatedPersonLevel;

    private String dockingPersonLevel;

    private String decisionMaker;

    private String relatedPersonPower;

    private String dockingPersonPower;

    private String decisionMakerPower;

    private String savingType;

    private String platformType;
    private String numberIntelligenceType;
    private String unmannedType;

    private String decarbonizationType;
    private String changeDeviceType;
    private String personNeed;
    private String achievementNeed;
    private String dropCostNeed;

    private Float businessRelationScore;

    private Float customerIntentionScore;

    private Float projectPotentialScore;

    private Integer evaluationSubmitBy;

    private LocalDateTime evaluationSubmitTime;
    private Integer createBy;

    private LocalDateTime createTime;

    private Integer updateBy;

    private LocalDateTime updateTime;

    private Integer isDelete;
    private Integer deleteBy;

    private LocalDateTime deleteTime;

}
