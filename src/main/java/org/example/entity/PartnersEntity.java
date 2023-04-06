package org.example.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 合作伙伴
 * </p>
 *
 * @author 文泽人
 * @since 2022-01-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)

public class PartnersEntity {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private Float score;

    private Integer createBy;

    private LocalDateTime createTime;

    private Integer updateBy;

    private LocalDateTime updateTime;

    private Integer discount;


}
