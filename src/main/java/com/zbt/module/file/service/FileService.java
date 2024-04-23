package com.zbt.module.file.service;

import com.zbt.module.activity.entity.ActivityFileEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 文件处理服务接口
 * 我所使用的是minio, 如果需要使用其它的存储服务, 可以实现该类
 */
public interface FileService {
    /**
     * 上传文件
     * @param inputStream
     * @param actId : 文件指向的activity_id
     * @return 访问路径
     */
    String uploadFile(InputStream inputStream, String fileName);

    /**
     * 下载文件
     * @return
     */
    byte[] downLoadFile(String url);

    /**
     * 删除文件
     * @param fileName
     */
    boolean delete(String url);

    /**
     * 上传图片
     * @param image
     * @param actId 图片指向的activity_id
     * @return
     */
    String uploadFile(MultipartFile image, String fileName);

    /**
     * 返回当前bucket下所有文件
     * @return
     */
    List<String> listAllFileInBucket();

    /**
     * 获取文件名称
     * todo: 如有需要, 请在业务逻辑中传递不同的回调函数, 以此实现更改文件名获取逻辑
     * @param callable 文件名的获取方式交由调用方实现
     * @return
     */
    String getFileName(Callable<String> callable);
}
