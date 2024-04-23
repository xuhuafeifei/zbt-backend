package com.zbt.module.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 下单表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName("tb_order")
public class OrderEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 订单时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime orderTime;
	/**
	 * 需求门店
	 */
	private String demandStore;
	/**
	 * 需求时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime demandTime;
	/**
	 * 物料
	 */
	private String material;
	/**
	 * 用途
	 */
	private String useCol;
	/**
	 * 节日
	 */
	private String festival;
	/**
	 * 专题
	 */
	private String topic;
	/**
	 * 品牌
	 */
	private String brand;
	/**
	 * 【1：正常，0：禁止】
	 */
	private Integer isDelete;
	/**
	 * 订单状态【待接单，进行中，待验收，已完成】
	 */
	private String status;
	/**
	 * 下单用户id
	 */
	private Integer userId;
	/**
	 * 下单用户姓名
	 */
	private String orderUserName;
	/**
	 * 关联的活动素材id
	 */
	private Integer actId;
	/**
	 * 活动名称
	 */
	private String actName;
	/**
	 * 完成时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime finishTime;
	/**
	 * 接单用户id
	 */
	private Integer receUserId;
}
