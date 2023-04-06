package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 空调节能项目技术评估表
 * </p>
 *
 * @author yuanzhan
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class ProjectAirConditionerTechnologyEvaluationEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private Integer projectId;

    private Integer energyType;

    private Float energyPrice;

    private Integer energyCostMode;

    private Integer energySettlementMode;

    private Integer energyCostCalculation;

    private Integer readMeterForm;

    private Float coldItemizedEnergyNum;

    private Float heatItemizedEnergyNum;

    private Integer createColdForm;

    private Integer createColdSourceType;

    private Integer createColdHasFrequencyConverter;

    private Integer createColdCorrespondingMode;

    private Integer createColdSettingType;

    private Integer createHeatForm;

    private Integer createHeatSourceType;

    private Integer createHeatHasFrequencyConverter;

    private Integer createHeatCorrespondingMode;

    private Integer createHeatSettingType;

    private Integer airConditioningTerminalForm;

    private Integer hasHeatWaterSystem;

    private Integer hasHeatNeed;

    private Integer hasColdNeed;

    private Integer cotrolSystemConf;

    private Integer controlMode;

    private Integer useStrength;

    private Integer dailyRunningTime;

    private Integer hasDefect;

    private Integer hasComplaint;

    private Integer hasColdHeatUneven;

    private Integer hasServiceUnit;

    private Integer hasMaintenancePlan;

    private Integer hasDataComplate;

    private Integer hasDeviceLedgerComplate;

    private Integer hasRunningRecordComplate;

    private Integer totalEvaluation;

    private Float projectScore;

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
