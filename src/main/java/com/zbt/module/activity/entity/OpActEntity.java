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
 * @date 2023-12-29 15:21:13
 */
@Data
@TableName("tb_op_act")
public class OpActEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 选项名称
	 */
	private String opName;
	/**
	 * 选项类型
	 */
	private String opType;
	/**
	 * 活动id
	 */
	private Integer actId;

}
