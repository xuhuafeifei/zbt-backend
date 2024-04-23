package com.zbt.module.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zbt.common.exception.ErrorCode;
import com.zbt.common.exception.RRException;
import com.zbt.common.utils.Constant;
import com.zbt.module.activity.entity.ReceOrderEntity;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.OrderDao;
import com.zbt.module.activity.entity.OrderEntity;
import com.zbt.module.activity.service.OrderService;


@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderDao, OrderEntity> implements OrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Object userId = params.get("userId");
        Object demandStore = params.get("demandStore");
        LambdaQueryWrapper<OrderEntity> wrapper = new LambdaQueryWrapper<>();

        // 判断是否按照userId字段查询
        if (userId != null && !"null".equals(userId)
                && !"NULL".equals(userId)
                && StringUtils.isNotBlank((String) userId))
        {
            wrapper.eq(OrderEntity::getUserId, Integer.valueOf((String) userId));
        }
        // 判断是否按照demandStore字段查询
        if (demandStore != null && !"null".equals(demandStore)
                && !"NULL".equals(demandStore)
                && StringUtils.isNotBlank((String) demandStore)
        ) {
            wrapper.eq(OrderEntity::getDemandStore, demandStore);
        }
        // 判断status状态
        if (params.get("status") != null) {
            String status = (String) params.get("status");
            // 如果status = "全部", 则查询全部, 不添加wrapper约束
            if (!"全部".equals(status)) {
                // 如果status != "全部", 且不符合订单状态, 抛出异常
                if (!Constant.OrderStatus.isValid(status)) {
                    throw new RRException("status数据缺失或不正确: " + status, ErrorCode.DATA_ERROR.getErrorCode());
                }
                // 添加status查询条件
                wrapper.eq(OrderEntity::getStatus, status);
            }
        }
        IPage<OrderEntity> page = this.page(
                new Query<OrderEntity>().getPage(params),
                wrapper
        );

        return new PageUtils(page);
    }

    @Autowired
    private ReceOrderServiceImpl receOrderService;

    /**
     * 验收
     * 修改order, rece_order
     *
     * @param orderId
     */
    @Override
    public void checkAndAccept(Integer orderId) {
        LocalDateTime now = LocalDateTime.now();
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(Constant.OrderStatus.COMPLETED.getValue());
        orderEntity.setFinishTime(now);
        this.updateById(orderEntity);
        // 修改rece_order
        ReceOrderEntity receOrderEntity = new ReceOrderEntity();
        receOrderEntity.setId(orderId);
        receOrderEntity.setFinishTime(now);
        receOrderService.updateById(receOrderEntity);
    }
}