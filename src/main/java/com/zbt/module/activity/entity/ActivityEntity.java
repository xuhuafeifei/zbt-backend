package com.zbt.module.activity.entity;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.*;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zbt.module.activity.dto.ActivityDto;
import com.zbt.module.activity.entity.handler.StringListTypeHandler;
import lombok.Data;

import javax.swing.text.html.HTMLDocument;

/**
 * 活动素材表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName(value = "tb_activity", autoResultMap = true)
public class ActivityEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@TableId
	private Integer id;
	/**
	 * 活动名称
	 */
	private String name;
	/**
	 * 上传时间
	 */
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private LocalDateTime uploadTime;
	/**
	 * 上传者
	 */
	private String uploader;
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
	 * 适用等级
	 */
	private String applicableGrade;
	/**
	 * 方案介绍
	 */
	private String schemeIntro;
	/**
	 * 【1：正常；0：禁止】
	 */
	private Integer isDelete;

	public ActivityDto toDto() {
	    ActivityDto activityDto = new ActivityDto();
	    activityDto.setId(id);
	    activityDto.setName(name);
	    activityDto.setUploadTime(uploadTime);
	    activityDto.setUploader(uploader);
	    activityDto.setMaterial(material);
	    activityDto.setUseCol(useCol);
	    activityDto.setFestival(festival);
	    activityDto.setTopic(topic);
	    activityDto.setBrand(brand);
	    activityDto.setApplicableGradeList(new ArrayList<>(Arrays.asList(applicableGrade.split(","))));
	    activityDto.setApplicableGrade(this.applicableGrade);
	    activityDto.setSchemeIntro(schemeIntro);
	    activityDto.setIsDelete(isDelete);
	    return activityDto;
	}
}
