package com.zbt.module.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2024-01-06 14:18:21
 */
@Data
@TableName("tb_user_info")
public class UserInfoEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 关联用户信息的具体内容
	 */
	private String infoContent;
	/**
	 * 关联用户
	 */
	private Integer userId;
	/**
	 * 内容类型
	 */
	private String infoType;

}
