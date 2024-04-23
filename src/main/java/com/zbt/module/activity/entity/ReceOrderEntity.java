package com.zbt.module.activity.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 接单表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName("tb_rece_order")
public class ReceOrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/*------------------接单表不允许逻辑删除, 否则可能会出现主键重复错误-----------------------*/

	/**
	 * 主键: 用户自己输入类型, 接单id = 订单id
	 */
	@TableId(type = IdType.INPUT)
	private Integer id;
	/**
	 * 接单时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderReceiveTime;
	/**
	 * 设计师
	 */
	private String designer;
	/**
	 * 传递初稿时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime firstDraftTime;
	/**
	 * 传源文件时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime firstSourcefileTime;
	/**
	 * 完成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime finishTime;
	/**
	 * 联系电话
	 */
	private String concatTelephone;
	/**
	 * 设计师id
	 */
	private Integer designerId;
}
