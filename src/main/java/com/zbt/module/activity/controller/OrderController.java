package com.zbt.module.activity.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbt.common.exception.ErrorCode;
import com.zbt.common.exception.RRException;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.common.utils.log.LogAnnotation;
import com.zbt.module.activity.entity.OrderEntity;
import com.zbt.module.activity.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;



/**
 * 下单表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    @LogAnnotation(module = "下单模块", operation = "分页查询下单",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = orderService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 查询所有demandStore信息
     */
    @RequestMapping(value = "/listAllDemandStore")
    public R listAll() {
        List<String> list = orderService.list(new QueryWrapper<OrderEntity>()
                .select("distinct demand_store")
                .isNotNull("demand_store")
                .orderByAsc("convert (demand_store using gbk)")
        ).stream().map(OrderEntity::getDemandStore).collect(Collectors.toList());
        return R.ok().put("data", list);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		OrderEntity order = orderService.getById(id);

        return R.ok().put("data", order);
    }

    /**
     * 下单
     */
    @RequestMapping("/save")
    @LogAnnotation(module = "下单模块", operation = "保存下单",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R save(@RequestBody OrderEntity order){
        Integer userId = order.getUserId();
        Integer actId = order.getActId();
        // 参数检验
        if (userId == null || actId == null) {
            throw new RRException("数据异常, userId = " + userId + " actId = " + actId
                    , ErrorCode.DATA_ERROR.getErrorCode());
        }
        if (order.getDemandStore() == null) {
            throw new RRException("需求门店数据异常, demandStore = " + order.getDemandStore(), ErrorCode.DATA_ERROR.getErrorCode());
        }
        /*------------------------重复下单限制被取消---------------------------*/
        // 判断是否重复下单
//        int count = orderService.count(new LambdaQueryWrapper<OrderEntity>()
//                .eq(OrderEntity::getActId, actId)
//                .eq(OrderEntity::getUserId, userId)
//        );
//        if (count != 0) {
//            throw new RRException(ErrorCode.REAPEAT_ORDER);
//        }
		orderService.save(order);

        return R.ok().put("data", order.getId());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @LogAnnotation(module = "下单模块", operation = "修改下单",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R update(@RequestBody OrderEntity order){
		orderService.updateById(order);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		orderService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

    /**
     * 验收
     */
    @RequestMapping("/checkAndAccept")
    @LogAnnotation(module = "订单模块", operation = "验收",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R checkAndAccept(@RequestParam Integer orderId) {
        orderService.checkAndAccept(orderId);
        return R.ok();
    }
}
