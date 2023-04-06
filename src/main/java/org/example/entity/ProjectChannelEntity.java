package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 项目渠道表
 * </p>
 *
 * @author lmj
 * @since 2023-02-09
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class ProjectChannelEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String projectChannel;

    private Integer createBy;

    private LocalDateTime createTime;

    private Integer updateBy;

    private LocalDateTime updateTime;

    private Integer isDelete;

    private Integer deleteBy;

    private LocalDateTime deleteTime;



}
