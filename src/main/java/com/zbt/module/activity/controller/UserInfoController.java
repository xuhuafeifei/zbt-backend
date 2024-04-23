package com.zbt.module.activity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.module.activity.entity.UserInfoEntity;
import com.zbt.module.activity.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2024-01-06 14:18:21
 */
@RestController
@RequestMapping("activity/userinfo")
public class UserInfoController {
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 根据用户id展示信息
     */
    @RequestMapping("/listByUserId")
    public R listByUserId(@RequestParam Integer userId, @RequestParam String infoType) {
        List<UserInfoEntity> list = userInfoService.list(new LambdaQueryWrapper<UserInfoEntity>()
                .eq(UserInfoEntity::getUserId, userId)
                .eq(UserInfoEntity::getInfoType, infoType)
        );
        return R.ok().put("data", list);
    }


    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userInfoService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		UserInfoEntity userInfo = userInfoService.getById(id);

        return R.ok().put("userInfo", userInfo);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserInfoEntity userInfo){
		userInfoService.save(userInfo);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserInfoEntity userInfo){
		userInfoService.updateById(userInfo);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		userInfoService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
