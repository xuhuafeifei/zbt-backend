package com.zbt.module.activity.dao;

import com.zbt.module.activity.entity.UserEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Mapper
public interface UserDao extends BaseMapper<UserEntity> {
	
}
