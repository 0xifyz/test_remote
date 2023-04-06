package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * <p>
 * 项目基本信息表，所有类型的项目通用字段
 * </p>
 *
 * @author yuanzhan
 * @since 2023-03-03
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class ProjectEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String projectCode;

    private String adcode;

    private Integer projectSource;

    private Integer projectChannelId;

    private String personChannel;

    private Integer projectTypeId;

    private String projectTypeOther;

    private Integer projectAreaId;

    private Integer projectReportState;

    private Integer aiotv5BuildingId;

    private String projectContactPerson;

    private String projectContactWay;

    private Double longitude;

    private Double latitude;

    private String province;

    private String city;

    private String district;

    private String address;

    private String note;

    private Integer enable;

    private Integer deleteBy;

    private LocalDateTime deleteTime;

    private Integer createBy;

    private LocalDateTime createTime;

    private Integer updateBy;

    private LocalDateTime updateTime;

    private Integer projectInfoState;

    private Integer projectCheckState;

    private Long projectCheckStateTime;


}
