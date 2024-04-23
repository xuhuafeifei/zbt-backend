package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.entity.OpActEntity;

import java.util.Map;

/**
 * 
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-29 15:21:13
 */
public interface OpActService extends IService<OpActEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

