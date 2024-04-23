package com.zbt.module.activity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.entity.ActivityFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * 活动图像源文件存储表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-13 21:10:13
 */
public interface ActivityFileService extends IService<ActivityFileEntity> {

    PageUtils queryPage(Map<String, Object> params);

    /**
     * 上传图片至minio, 并将图片信息存储到tb_activity_file中
     * @param file
     * @param actId
     * @param fileType
     * @return
     */
    ActivityFileEntity uploadFile(MultipartFile file, Integer actId, String fileType);

    /**
     * 批量上传图片至minio, 并将图片信息存储到tb_activity_file中
     * @param fileList
     * @param actId
     * @param fileType
     * @return
     */
    List<ActivityFileEntity> uploadFileList(List<MultipartFile> fileList, Integer actId, String fileType);

    /**
     * 删除minio中存储的图片, 同时删除tb_activity_file中的信息
     * @param id
     * @param url
     * @return
     */
    @Deprecated // 如果遇到批量删除, 速度会大幅度减缓. 请自行编写异步逻辑
    boolean delete(Integer id, String url);

    /**
     * 批量删除minio中存储的图片, 同时删除tb_activity_file中的信息
     * @param list
     */
    @Deprecated // 如果遇到批量删除, 速度会大幅度减缓. 请自行编写异步逻辑
    void deleteFileList(List<ActivityFileEntity> list);

    /**
     * 传递初稿
     * @return
     */
    String uploadFirstDraft(MultipartFile file, Integer actId, String fileType, Integer orderId);

    /**
     * 传递源文件
     */
    void uploadFirstSourcefile(MultipartFile file, Integer actId, String fileType, Integer orderId);

    /**
     * 接收富文本框传递的图片
     * @param file
     * @return
     */
    Object uploadFullTextPict(MultipartFile file);
}

