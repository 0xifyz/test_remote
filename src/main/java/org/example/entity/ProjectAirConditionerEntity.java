package org.example.entity;


import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 空调节能项目表
 * </p>
 *
 * @author yuanzhan
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectAirConditionerEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;
    
    private Integer projectId;

    private Integer creatColdForm;

    private Integer creatHeatForm;
    
    private String creatColdOther;
    
    private String creatHeatOther;

    private Integer buildingCount;

    private Float energyCost;

    private Long serviceTime;

    private Float constructionArea;

    private Integer projectFormatId;

    private String projectFormatOther;

    
    private Integer createBy;

    
    private LocalDateTime createTime;

    private Integer updateBy;

    private LocalDateTime updateTime;
    
    private Integer isDelete;
    
    private Integer deleteBy;

    private LocalDateTime deleteTime;


}
