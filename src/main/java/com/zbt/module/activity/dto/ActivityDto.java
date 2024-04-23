package com.zbt.module.activity.dto;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.zbt.common.utils.Constant;
import com.zbt.module.activity.entity.ActivityEntity;
import com.zbt.module.activity.entity.ActivityFileEntity;
import com.zbt.module.activity.entity.OpActEntity;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动素材表
 * 
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@Data
@TableName(value = "tb_activity", autoResultMap = true)
public class ActivityDto implements Serializable {
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
	 * 适用等级集合
	 */
	private List<String> applicableGradeList;
	/**
	 * 适用等级
	 */
	private String applicableGrade;
	/**
	 * 活动图片集合
	 */
	private List<ActivityFileEntity> picturesUrl;
	/**
	 * 方案介绍
	 */
	private String schemeIntro;
	/**
	 * psd源文件链接集合
	 */
	private List<ActivityFileEntity> sourcefilesUrl;
	/**
	 * 【1：正常；0：禁止】
	 */
	private Integer isDelete;

	/**
	 * 材料list
	 */
	private List<String> materialList;
	private List<String> useColList;
	private List<String> festivalList;
	private List<String> topicList;
	private List<String> brandList;

	/**
	 * 选项集合
	 */
	private List<OpActEntity> optionList;

	public ActivityEntity toEntity() {
	    ActivityEntity activityEntity = new ActivityEntity();
	    activityEntity.setId(id);
	    activityEntity.setName(name);
	    activityEntity.setUploadTime(uploadTime);
	    activityEntity.setUploader(uploader);
	    activityEntity.setMaterial(material);
	    activityEntity.setUseCol(useCol);
	    activityEntity.setFestival(festival);
	    activityEntity.setTopic(topic);
	    activityEntity.setBrand(brand);
	    activityEntity.setApplicableGrade(String.join(Constant.ACTIVITY_DELIMITER, applicableGradeList));
	    activityEntity.setSchemeIntro(schemeIntro);
	    activityEntity.setIsDelete(isDelete);
	    return activityEntity;
	}

	/**
	 * 将optionList中的所有元素, 按照opType进行划分, 填充在dto不同的List中
	 */
	public void handleOptionList() {
		this.materialList = new ArrayList<>();
		this.useColList = new ArrayList<>();
		this.festivalList = new ArrayList<>();
		this.topicList = new ArrayList<>();
		this.brandList = new ArrayList<>();

	    optionList.forEach(e -> {
			String opType = e.getOpType();
			String opName = e.getOpName();
			if (Constant.ActivityOptionType.MATERIAL.getValue().equals(opType)) {
				materialList.add(opName);
			}else if (Constant.ActivityOptionType.USE.getValue().equals(opType)) {
				useColList.add(opName);
			}else if (Constant.ActivityOptionType.BRAND.getValue().equals(opType)) {
				brandList.add(opName);
			}else if (Constant.ActivityOptionType.FESTIVAL.getValue().equals(opType)) {
				festivalList.add(opName);
			}else if (Constant.ActivityOptionType.TOPIC.getValue().equals(opType)) {
				topicList.add(opName);
			}
		});
	}

	public List<OpActEntity> getOptionList(Integer actId) {
		ArrayList<OpActEntity> optionList = new ArrayList<>();
		// 处理material
		materialList.forEach(e -> {
			OpActEntity entity = new OpActEntity();
			entity.setOpName(e);
			entity.setActId(actId);
			entity.setOpType(Constant.ActivityOptionType.MATERIAL.getValue());
			optionList.add(entity);
		});
		// 处理useList
		useColList.forEach(e -> {
			OpActEntity entity = new OpActEntity();
			entity.setOpName(e);
			entity.setActId(actId);
			entity.setOpType(Constant.ActivityOptionType.USE.getValue());
			optionList.add(entity);
		});
		// 处理brandList
		brandList.forEach(e -> {
			OpActEntity entity = new OpActEntity();
			entity.setOpName(e);
			entity.setActId(actId);
			entity.setOpType(Constant.ActivityOptionType.BRAND.getValue());
			optionList.add(entity);
		});
		// 处理festivalList
		festivalList.forEach(e -> {
			OpActEntity entity = new OpActEntity();
			entity.setOpName(e);
			entity.setActId(actId);
			entity.setOpType(Constant.ActivityOptionType.FESTIVAL.getValue());
			optionList.add(entity);
		});
		// 处理topicList
		topicList.forEach(e -> {
			OpActEntity entity = new OpActEntity();
			entity.setOpName(e);
			entity.setActId(actId);
			entity.setOpType(Constant.ActivityOptionType.TOPIC.getValue());
			optionList.add(entity);
		});
		return optionList;
	}

	/**
	 * 将picturesUrl中的内容, 按照type进行分类, 在dto中赋值
	 */
	public void handleActivityFileEntity() {
		// 创建两个新列表：一个用于存储图片URL，另一个用于存储源文件URL
		List<ActivityFileEntity> pictures = new ArrayList<>();
		List<ActivityFileEntity> sourceFiles = new ArrayList<>();

		// 遍历picturesUrl并根据类型分类
		this.getPicturesUrl().forEach(fileEntity -> {
			if ("pict".equals(fileEntity.getType())) {
				pictures.add(fileEntity);
			} else {
				sourceFiles.add(fileEntity);
			}
		});

		// 更新ActivityDto对象的picturesUrl和sourcefilesUrl属性
		this.setPicturesUrl(pictures);
		this.setSourcefilesUrl(sourceFiles);
	}

	/**
	 * 将选项list转换为String
	 */
	public void handleOptionListToString() {
		this.material = String.join(Constant.ACTIVITY_DELIMITER, this.materialList);
		this.brand = String.join(Constant.ACTIVITY_DELIMITER, this.brandList);
		this.topic = String.join(Constant.ACTIVITY_DELIMITER, this.topicList);
		this.festival = String.join(Constant.ACTIVITY_DELIMITER, this.festivalList);
		this.useCol = String.join(Constant.ACTIVITY_DELIMITER, this.useColList);
	}
}
