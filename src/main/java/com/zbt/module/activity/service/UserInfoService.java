package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.entity.UserInfoEntity;

import java.util.Map;

/**
 * 
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2024-01-06 14:18:21
 */
public interface UserInfoService extends IService<UserInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

