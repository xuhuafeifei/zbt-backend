package com.zbt.module.activity.dao;

import com.zbt.common.utils.Constant;
import com.zbt.module.activity.dto.ActivityDto;
import com.zbt.module.activity.entity.ActivityEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zbt.module.activity.entity.ConditionActivity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 活动素材表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Mapper
public interface ActivityDao extends BaseMapper<ActivityEntity> {

    /**
     * 查询activity表, 返回dto数据
     * @return
     * @param limit
     * @param offsetNumber
     */
    List<ActivityDto> getActivityDto(@Param("limit") long limit, @Param("offset") long offsetNumber);

    /**
     * 条件查询activity表, 返回dto数据
     * @param limit
     * @param offsetNumber
     * @param cond
     * @return
     */
    List<ActivityDto> getActivityDtoWithCondition(@Param("limit") long limit, @Param("offset") long offsetNumber,
                                                  @Param("cond")ConditionActivity cond);
}
