package com.zbt.module.activity.controller;

import com.zbt.common.utils.Constant;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.module.activity.entity.OrderEntity;
import com.zbt.module.activity.entity.ReceOrderEntity;
import com.zbt.module.activity.service.OrderService;
import com.zbt.module.activity.service.ReceOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 接单表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/receorder")
public class ReceOrderController {
    @Autowired
    private ReceOrderService receOrderService;

    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = receOrderService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ReceOrderEntity receOrder = receOrderService.getById(id);

        return R.ok().put("data", receOrder);
    }

    /**
     * 接单锁
     */
    private static final Object receiveOrderLock = new Object();

    /**
     * 接单
     */
    @RequestMapping("/save")
    public R save(@RequestBody ReceOrderEntity receOrder){
        // 保证接单操作原子性, 防止同时有其它线程进行接单
        // todo: 如果后续升级为分布式项目, 请将锁升级成分布式锁
        synchronized (receiveOrderLock) {
            // 同一个订单只允许被接单一次, 反复接单会出现mysql主键重复错误, 进而组织重复接单
            receOrderService.save(receOrder);
            // 修改订单状态
            OrderEntity orderEntity = new OrderEntity();
            // 设置订单id
            orderEntity.setId(receOrder.getId());
            // 设置订单状态
            orderEntity.setStatus(Constant.OrderStatus.IN_PROGRESS.getValue());
            // 设置接单人的id
            orderEntity.setReceUserId(receOrder.getDesignerId());
            orderService.updateById(orderEntity);

            return R.ok();
        }
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody ReceOrderEntity receOrder){
		receOrderService.updateById(receOrder);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		receOrderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
