package com.zbt.module.activity.controller;

import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.common.utils.log.LogAnnotation;
import com.zbt.module.activity.entity.CommunicateRecordEntity;
import com.zbt.module.activity.service.CommunicateRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;



/**
 * 沟通记录表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/communicaterecord")
public class CommunicateRecordController {
    @Autowired
    private CommunicateRecordService communicateRecordService;

    /**
     * 根据order_id, 查询贺order_id有关的全部沟通信息
     */
    @RequestMapping("listAll")
    @LogAnnotation(module = "沟通记录模块", operation = "根据orderId, 查询相关的全部沟通记录",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R listAllByOrderId(@RequestParam Integer orderId) {
        return communicateRecordService.listAllByOrderId(orderId);
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = communicateRecordService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		CommunicateRecordEntity communicateRecord = communicateRecordService.getById(id);

        return R.ok().put("communicateRecord", communicateRecord);
    }

    /**
     * 保存沟通记录
     */
    @RequestMapping("/save")
    public R save(@RequestBody CommunicateRecordEntity communicateRecord){
        communicateRecord.setUploadTime(LocalDateTime.now());
		communicateRecordService.save(communicateRecord);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody CommunicateRecordEntity communicateRecord){
		communicateRecordService.updateById(communicateRecord);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		communicateRecordService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
