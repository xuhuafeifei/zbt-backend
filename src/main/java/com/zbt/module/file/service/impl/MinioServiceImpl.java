package com.zbt.module.file.service.impl;

import com.zbt.common.utils.MinioConfig;
import com.zbt.module.activity.entity.ActivityFileEntity;
import com.zbt.module.activity.service.ActivityFileService;
import com.zbt.module.file.service.FileService;
import com.zbt.module.file.utils.FileUtils;
import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Item;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

/**
 * 使用minio实现文件存储
 */
@Slf4j
@Component("minioService")
public class MinioServiceImpl implements FileService {
    @Autowired
    private MinioConfig minioConfig;

    private MinioClient getMinioClient() {
        return MinioClient.builder()
                .endpoint(minioConfig.getEndpoint())
                .credentials(minioConfig.getAccessKey(), minioConfig.getSecretKey())
                .build();
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @param fileName
     * @return 访问路径
     */
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        MinioClient client = getMinioClient();
        try(InputStream stream = inputStream) {
            boolean bucketExists = client.bucketExists(BucketExistsArgs.builder().bucket(minioConfig.getBucketName()).build());
            if (!bucketExists) {
                client.makeBucket(
                        MakeBucketArgs.builder()
                                .bucket(minioConfig.getBucketName())
                                .objectLock(true)
                                .build());
                log.info(minioConfig.getBucketName() + " 创建成功");
            }
            client.putObject(PutObjectArgs.builder()
                    .stream(stream, inputStream.available(), PutObjectArgs.MAX_PART_SIZE)
                    .object(fileName)
                    .bucket(minioConfig.getBucketName())
                    .build());
            String fileUrl =
                    client.getPresignedObjectUrl(
                            GetPresignedObjectUrlArgs.builder()
                                    .method(Method.GET)
                                    .bucket(minioConfig.getBucketName())
                                    .object(fileName)
                                    .build());
            String[] split = fileUrl.split("\\?");
            log.info("url: " + split[0]);
            return split[0];

        } catch (Exception e) {
            log.info("文件上传失败");
            throw new RuntimeException(e);
        }
    }



    /**
     * 下载文件
     *
     * @param url
     * @return
     */
    @Override
    public byte[] downLoadFile(String url) {
        MinioClient minioClient = getMinioClient();
        try{
            InputStream inputStream = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(url)
                    .build());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            int len;
            byte[] data = new byte[1024];
            while( (len = inputStream.read(data, 0, data.length)) != 0) {
                baos.write(data, 0, len);
            }
            baos.flush();
            return baos.toByteArray();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new byte[0];
    }

    /**
     * 删除文件
     *
     */
    @Override
    public boolean delete(String url) {
        try {
            String fileName = FileUtils.getFilenameFromURL(url);
            log.info("fileName: " + fileName);
            MinioClient minioClient = getMinioClient();

            // 删除minio中的数据
            minioClient.removeObject(RemoveObjectArgs.builder()
                    .bucket(minioConfig.getBucketName())
                    .object(fileName).build()
            );
            return true;
        }catch (Exception e){
            log.info("文件删除失败" + e.getMessage());
            return false;
        }
    }

    /**
     * 上传图片
     *
     * @param image
     * @param fileName
     * @return
     */
    @Override
    public String uploadFile(MultipartFile image, String fileName) {
        try {
            return uploadFile(image.getInputStream(), fileName);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回当前bucket下所有文件
     *
     * @return
     */
    @Override
    public List<String> listAllFileInBucket() {
        List<String> list = new ArrayList<>();
        try {
            // 初始化MinioClient
            MinioClient minioClient = getMinioClient();

            // 获取存储桶中的对象列表
            Iterable<Result<Item>> objects = minioClient.listObjects(
                    ListObjectsArgs.builder()
                            .bucket(minioConfig.getBucketName())
                            .build()
            );

            for (Result<Item> result : objects) {
                Item item = result.get();
                String url = minioConfig.getEndpoint() + minioConfig.getBucketName()
                        + "/" + item.objectName();
                list.add(url);
            }
            return list;
        } catch (Exception e) {
            log.info("获取错误: " + e);
            return null;
        }
    }

    /**
     * 获取文件名称
     */
    @Override
    public String getFileName(Callable<String> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            log.error("回调函数报错, 返回随机文件名");
            return UUID.randomUUID().toString().substring(0, 10);
        }
    }
}
