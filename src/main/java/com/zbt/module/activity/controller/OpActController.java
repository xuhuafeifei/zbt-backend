package com.zbt.module.activity.controller;

import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.module.activity.entity.OpActEntity;
import com.zbt.module.activity.service.OpActService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;



/**
 * 
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-29 15:21:13
 */
@RestController
@RequestMapping("activity/opact")
public class OpActController {
    @Autowired
    private OpActService opActService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = opActService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		OpActEntity opAct = opActService.getById(id);

        return R.ok().put("opAct", opAct);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody OpActEntity opAct){
		opActService.save(opAct);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OpActEntity opAct){
		opActService.updateById(opAct);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		opActService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
