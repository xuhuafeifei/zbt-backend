package com.zbt.module.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zbt.common.exception.ErrorCode;
import com.zbt.common.exception.RRException;
import com.zbt.common.utils.JWTUtils;
import com.zbt.module.activity.vo.LoginVo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.UserDao;
import com.zbt.module.activity.entity.UserEntity;
import com.zbt.module.activity.service.UserService;


@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<UserEntity> page = this.page(
                new Query<UserEntity>().getPage(params),
                new QueryWrapper<UserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public LoginVo login(String username, String password) {
        // 获取对象
        UserEntity entity = this.getOne(new LambdaQueryWrapper<UserEntity>()
                .eq(UserEntity::getStatus, "启用")
                .eq(UserEntity::getUsername, username));
        if (entity == null) {
            throw new RRException(ErrorCode.USER_NOT_FOUND);
        }
        if (!entity.getPassword().equals(password)) {
            throw new RRException(ErrorCode.PASSWORD_NOT_EQUAL);
        }
        LoginVo loginVo = new LoginVo();
        List<String> roles = new ArrayList<>();
        roles.add(entity.getRoles());
        loginVo.setRoles(roles);
        loginVo.setUsername(username);
        loginVo.setAccessToken(JWTUtils.sign(Long.valueOf(entity.getId())));
        loginVo.setRefreshToken(JWTUtils.sign(Long.valueOf(entity.getId())));
        loginVo.setId(entity.getId());
        loginVo.setName(entity.getName());
        // 存储redis todo: redis暂时不适用
        // cacheService.setEx(RedisConstant.USER + entity.getId(), gson.toJson(loginVo), 2, TimeUnit.DAYS);
        return loginVo;
    }

}