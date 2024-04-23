package com.zbt.module.activity.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zbt.module.activity.entity.handler.StringListTypeHandler;
import lombok.Data;
import org.simpleframework.xml.Transient;

/**
 * 沟通记录表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName(value = "tb_communicate_record", autoResultMap = true)
public class CommunicateRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId(type = IdType.AUTO)
	private Integer id;
	/**
	 * 消息的父消息id
	 */
	private Integer pid;
	/**
	 * 消息发送者的id
	 */
	private Integer uploaderId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 消息关联的order_id
	 */
	private Integer OrderId;
	/**
	 * 消息发送者的姓名
	 */
	private String uploaderName;
	/**
	 * 消息发送时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime uploadTime;

	@TableField(exist = false)
	private List<CommunicateRecordEntity> childrenList;
}
