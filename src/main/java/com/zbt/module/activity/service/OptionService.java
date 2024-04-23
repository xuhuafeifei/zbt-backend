package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.entity.OptionEntity;

import java.util.Map;

/**
 * 选项表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
public interface OptionService extends IService<OptionEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

