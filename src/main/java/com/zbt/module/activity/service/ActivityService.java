package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.dto.OptionDto;
import com.zbt.module.activity.dto.OptionSelectDto;
import com.zbt.module.activity.entity.ActivityEntity;
import com.zbt.module.activity.entity.ConditionActivity;

import java.util.Map;

/**
 * 活动素材表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
public interface ActivityService extends IService<ActivityEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 条件分页查询
     * @param params
     * @return
     */
    PageUtils queryPageWithCondition(ConditionActivity ca);

    /**
     * 查询所有'活动库'相关的选项
     * @return
     */
    OptionDto listOptions();

    /**
     * 查询所有'活动库' 相关的选项, 并以Select {label: '', value: ''} 形式返回
     * @return
     */
    OptionSelectDto listOptionSelect();

    /**
     * 删除素材
     * @param actId
     */
    void delete(Integer actId);

    /**
     * 返回路由
     * @return
     */
    Object getAsyncRoutes();
}

