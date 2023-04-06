package org.example.util;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.db.Db;
import cn.hutool.db.Entity;
import cn.hutool.db.Session;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.*;

import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * program: data-migrate
 * <p>
 * 功能描述: 项目数据转移工具类
 *
 * @author : YuanZ
 * @since : 2023-03-08 17:44
 **/
@Slf4j
public class ProjectDataTransferUtil {

    public static List<BuildingEntity> getBuildingList() throws SQLException {
        log.info("开始查询v4项目表数据");
        final List<BuildingEntity> query = Db.use().query("select * from t_building", BuildingEntity.class);
        log.info("查询完毕");
        return query;
    }

    /**
     * 查出v4的合作伙伴的表中数据，然后插入到v5的项目渠道表
     * @return
     * @throws SQLException
     */
    public static void insertPartnerList() throws SQLException {
        log.info("查询v4项目渠道表");
        final List<PartnersEntity> partnersEntities = Db.use().query("select * from t_partners", PartnersEntity.class);
        log.info("查询完毕");
        List<Entity> entityList = new ArrayList<>();
        for (PartnersEntity item : partnersEntities) {
            // 将合作伙伴实体转为项目渠道实体
            final ProjectChannelEntity projectChannelEntity = new ProjectChannelEntity();
            BeanUtil.copyProperties(item,projectChannelEntity);
            if(StrUtil.isNotBlank(item.getName())){
                projectChannelEntity.setProjectChannel(item.getName());
            }
            final Entity entity = Entity.parseWithUnderlineCase(projectChannelEntity);
            entity.setTableName("lianjiev5.t_project_channel");
            entityList.add(entity);
        }
        try(
                Session session = Session.create()
                ){
            session.beginTransaction();
            session.insert(entityList);
            session.commit();
        }
        log.info("插入项目渠道数据完成");
        
    }

    
    

    /**
     * 整理需要添加的记录实体
     *
     * @return
     * @throws SQLException
     */
    public static DataEntity getEntity() throws SQLException {

        final DataEntity dataEntity = new DataEntity();
        final List<BuildingEntity> buildingList = getBuildingList();
        // t_project
        final List<ProjectEntity> projectEntityList = buildingList.stream().map(convent).collect(Collectors.toList());
        // t_project_air_conditioner
        final List<ProjectAirConditionerEntity> airConditionerEntities = buildingList.stream().map(conventToAir).collect(Collectors.toList());
        // 技术评估集合
        List<ProjectAirConditionerTechnologyEvaluationEntity> technologyEvaluationEntityList = new ArrayList<>();
        // 商务评估集合
        List<ProjectAirConditionerBusinessEvaluationEntity> businessEvaluationEntityList = new ArrayList<>();
        for (BuildingEntity buildingEntity : buildingList) {
            final ProjectAirConditionerTechnologyEvaluationEntity technologyEvaluationEntity = new ProjectAirConditionerTechnologyEvaluationEntity();
            final ProjectAirConditionerBusinessEvaluationEntity businessEvaluationEntity = new ProjectAirConditionerBusinessEvaluationEntity();
            technologyEvaluationEntity
                    .setId(buildingEntity.getId())
                    .setProjectId(buildingEntity.getId())
                    .setCreateBy(buildingEntity.getCreateBy())
                    .setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(buildingEntity.getCreateTime()), ZoneOffset.of("+8")));
            technologyEvaluationEntityList.add(technologyEvaluationEntity);
            businessEvaluationEntity
                    .setId(buildingEntity.getId())
                    .setProjectId(buildingEntity.getId())
                    // 设置综合评估
                    .setBusinessRelationScore(buildingEntity.getBusinessRelationRating())
                    .setCustomerIntentionScore(buildingEntity.getCustomerWillRating())
                    .setProjectPotentialScore(buildingEntity.getProjectPotentialRating())
                    .setCreateBy(buildingEntity.getCreateBy())
                    .setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(buildingEntity.getCreateTime()), ZoneOffset.of("+8")));
            businessEvaluationEntityList.add(businessEvaluationEntity);
        }
        dataEntity.setProjectEntityList(projectEntityList);
        dataEntity.setProjectAirConditionerEntityList(airConditionerEntities);
        dataEntity.setBusinessEvaluationEntities(businessEvaluationEntityList);
        dataEntity.setTechnologyEvaluationEntityList(technologyEvaluationEntityList);
        return dataEntity;
    }
    

    /**
     * 将v4的项目数据转为v5的项目基本信息格式再插入数据库
     */
    static Function<BuildingEntity, ProjectEntity> convent = item -> {
        final ProjectEntity projectEntity = new ProjectEntity();
        // 属性拷贝
        BeanUtil.copyProperties(item, projectEntity);
        if (item.getPartnerId() != null) {
            projectEntity.setProjectChannelId(item.getPartnerId());
            
        }
        projectEntity.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getCreateTime()), ZoneOffset.of("+8")));
        // 如果更新时间不为空，旧将更新时间转换后拷贝过来
        if (item.getUpdateTime() != null) {
            projectEntity.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getUpdateTime()), ZoneOffset.of("+8")));
        }
        // v4 已经暂停的项目 在v5同样暂停
        projectEntity.setEnable(1);
        if (item.getIsEnd() == 2) {
            projectEntity.setEnable(2);
        }
        // 将项目类型设置为节能项目
        projectEntity.setProjectTypeId(1);
        return projectEntity;

    };

    /**
     * 向数据库中插入数据
     */
    public static void insertProjectEntity() throws SQLException {
        List<Entity> entityList = new ArrayList<>();
        try (
                Session session = Session.create()
        ) {
            
            final DataEntity dataEntity = getEntity();
            for (ProjectEntity projectEntity : dataEntity.getProjectEntityList()) {
                final Entity entity = Entity.parseWithUnderlineCase(projectEntity);
                entity.setTableName("lianjiev5.t_project");
                entityList.add(entity);
            }
            session.beginTransaction();
            session.insert(entityList);  // 插入数据
            session.commit(); // 提交事务
            log.info("t_project插入成功");
        }
    }

    public static void insertProjectAirCondition() throws SQLException {
        List<Entity> entityList = new ArrayList<>();
        try (
                Session session = Session.create()
        ) {
            
            final DataEntity dataEntity = getEntity();
           
            for (ProjectAirConditionerEntity projectAirConditionerEntity : dataEntity.getProjectAirConditionerEntityList()) {
                final Entity entity = Entity.parseWithUnderlineCase(projectAirConditionerEntity);
                entity.setTableName("lianjiev5.t_project_air_conditioner");
                entityList.add(entity);
            }
            session.beginTransaction();
            session.insert(entityList);  // 插入数据
            session.commit(); // 提交事务
            log.info("t_project_air_conditioner插入成功");
        }
    }

    public static void insertProjectAirConditionBusinessEval() throws SQLException {
        List<Entity> entityList = new ArrayList<>();
        try (
                Session session = Session.create()
        ) {
            final DataEntity dataEntity = getEntity();
            for (ProjectAirConditionerBusinessEvaluationEntity businessEvaluationEntity : dataEntity.getBusinessEvaluationEntities()) {
                final Entity entity = Entity.parseWithUnderlineCase(businessEvaluationEntity);
                entity.setTableName("lianjiev5.t_project_air_conditioner_business_evaluation");
                entityList.add(entity);
            }
            session.beginTransaction();
            session.insert(entityList);  // 插入数据
            session.commit(); // 提交事务
            log.info("t_project_air_conditioner_business插入成功");
        }
    }

    public static void insertProjectAirConditionTechnologyEval() throws SQLException {
        List<Entity> entityList = new ArrayList<>();
        try (
                Session session = Session.create()
        ) {
           
            final DataEntity dataEntity = getEntity();
           
            for (ProjectAirConditionerTechnologyEvaluationEntity technologyEvaluationEntity : dataEntity.getTechnologyEvaluationEntityList()) {
                final Entity entity = Entity.parseWithUnderlineCase(technologyEvaluationEntity);
                entity.setTableName("lianjiev5.t_project_air_conditioner_technology_evaluation");
                entityList.add(entity);
            }
            session.beginTransaction();
            session.insert(entityList);  // 插入数据
            session.commit(); // 提交事务
            log.info("t_project_air_conditioner_technology_eval插入成功");
        }
    }

    /**
     * 将building实体转为空间项目实体
     */
    static Function<BuildingEntity, ProjectAirConditionerEntity> conventToAir = item -> {
        final ProjectAirConditionerEntity projectAirConditionerEntity = new ProjectAirConditionerEntity();
        // 属性拷贝
        BeanUtil.copyProperties(item, projectAirConditionerEntity);
        // 将BuildingEntity的id设置为 ProjectAirConditionerEntity 的projectId
        projectAirConditionerEntity.setProjectId(item.getId());
        // 赋值制冷形式
        if (StrUtil.isNotBlank(item.getRefrigerationType())) {
            projectAirConditionerEntity.setCreatColdForm(getColdFormId(item.getRefrigerationType()));
        }
        // 赋值制冷形式其他
        if (StrUtil.isNotBlank(item.getRefrigerationTypeOther())) {
            projectAirConditionerEntity.setCreatColdOther(item.getRefrigerationTypeOther());
        }
        // 赋值制热形式
        if (StrUtil.isNotBlank(item.getHeatingType())) {
            projectAirConditionerEntity.setCreatHeatForm(getHeatFormId(item.getHeatingType()));
        }
        // 赋值制冷形式其他
        if (StrUtil.isNotBlank(item.getHeatingTypeOther())) {
            projectAirConditionerEntity.setCreatHeatOther(item.getHeatingTypeOther());
        }
        // 建筑面积
        if (item.getArea() != null) {
            projectAirConditionerEntity.setConstructionArea(item.getArea());
        }

        // 设置创建时间
        projectAirConditionerEntity.setCreateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getCreateTime()), ZoneOffset.of("+8")));
        // 如果更新时间不为空，旧将更新时间转换后拷贝过来
        if (item.getUpdateTime() != null) {
            projectAirConditionerEntity.setUpdateTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(item.getUpdateTime()), ZoneOffset.of("+8")));
        }
        return projectAirConditionerEntity;
    };

    /**
     * @param form
     * @return
     */
    public static int getColdFormId(String form) {
        switch (form) {
            case "中央空调":
                return 1;
            case "分体空调":
                return 2;
            case "多联机VRV":
                return 3;
            case "其它":
                return 4;
        }
        return 0;
    }

    public static int getHeatFormId(String form) {
        switch (form) {
            case "无供暖":
                return 1;
            case "市政":
                return 2;
            case "锅炉":
                return 3;
            case "热泵":
                return 4;
            case "分体空调":
                return 5;
            case "多联机VRV":
                return 6;
            case "其他":
                return 7;
        }
        return 0;
    }

    public static void main(String[] args) throws SQLException {
       // System.out.println(getEntity().getProjectEntityList());
        insertPartnerList();
        insertProjectEntity();
        insertProjectAirCondition();
        insertProjectAirConditionBusinessEval();
        insertProjectAirConditionTechnologyEval();
    }
}
