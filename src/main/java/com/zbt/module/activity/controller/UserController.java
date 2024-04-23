package com.zbt.module.activity.controller;

import java.util.Arrays;
import java.util.Map;

import com.zbt.common.utils.Res;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.zbt.module.activity.entity.UserEntity;
import com.zbt.module.activity.service.UserService;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;



/**
 * 用户表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 登录接口
     */
    @GetMapping("login")
    public Res login(@RequestParam String username, String password) {
        return Res.ok().put("data", userService.login(username, password));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = userService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		UserEntity user = userService.getById(id);

        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody UserEntity user){
		userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody UserEntity user){
		userService.updateById(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		userService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
