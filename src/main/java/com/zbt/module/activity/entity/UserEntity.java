package com.zbt.module.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 用户表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName("tb_user")
public class UserEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 姓名
	 */
	private String name;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 【1：正常，0：禁止】
	 */
	private Integer isDelete;
	/**
	 * 等级【白银会员，黄金会员，钻石会员】
	 */
	private String level;
	/**
	 * 角色【设计师，用户，操作员】
	 */
	private String roles;
	/**
	 * 部门
	 */
	private String depart;
	/**
	 * 联系电话
	 */
	private String telephone;
	/**
	 * 用户状态
	 */
	private String status;
}
