package com.zbt.module.activity.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

/**
 * 活动图像源文件存储表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-13 21:10:13
 */
@Data
@TableName("tb_activity_file")
public class ActivityFileEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 关联的活动id
	 */
	private Integer actId;
	/**
	 * url
	 */
	private String url;
	/**
	 * 文件类型【pict/sourcefile】
	 */
	private String type;
	/**
	 * 是否被删除
	 */
	private Integer isDelete;
	/**
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime uploadTime;

	public ActivityFileEntity() {}
}
