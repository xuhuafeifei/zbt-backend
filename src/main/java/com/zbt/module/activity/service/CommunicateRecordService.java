package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.module.activity.entity.CommunicateRecordEntity;

import java.util.Map;

/**
 * 沟通记录表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
public interface CommunicateRecordService extends IService<CommunicateRecordEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 查询沟通记录
     * @param orderId
     * @return
     */
    R listAllByOrderId(Integer orderId);
}

