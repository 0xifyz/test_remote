package org.example.entity;

import lombok.Data;

import javax.management.relation.Relation;

/**
 * <p>
 *
 * </p>
 *
 * @author 张通达
 * @since 2022-06-01
 */

@Data
public class BuildingEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String projectCode;

    private String adcode;

    private Double longitude;

    private Double latitude;

    private String province;

    private String city;

    private String district;

    private String address;

    private String note;

    private Float area;

    private String locationType;

    private Integer createBy;

    private Long createTime;
    
    private Integer updateBy;

    private Long updateTime;
    
    private String upFloorNumber;
    
    private String underFloorNumber;

    private Float electricUnitPrice;

    private Integer electricBillingDate;

    private Float fuelGasUnitPrice;

    private Integer fuelGasBillingDate;

    private Integer propertyManagementMode;

    private Integer propertyAttribute;

    private String locationTypeOther;

    private Integer partnerId;

    private Float putTime;

    private String refrigerationType;

    private String refrigerationTypeOther;

    private Integer isEnd;

    private String heatingType;

    private String heatingTypeOther;
    
    private String clientFunctionalRequirement;

    private Integer energySavingQuarter;

    private String clientBusinessRequirement;

    private Integer projectInfoSubmitBy;

    private Long projectInfoSubmitTime;
    
    private Integer aiotv5BuildingId;
    
    private Float businessRelationRating;
    private Float customerWillRating;
    private Float projectPotentialRating;

    private Integer projectInfoState;

    private Integer projectCheckState;

    private Long projectCheckStateTime;

}
