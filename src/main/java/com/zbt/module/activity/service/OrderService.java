package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.entity.OrderEntity;

import java.util.Map;

/**
 * 下单表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
public interface OrderService extends IService<OrderEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 验收
     * 修改order, rece_order
     * @param orderId
     */
    void checkAndAccept(Integer orderId);
}

