package com.zbt.module.file.service.impl;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.OSSObjectSummary;
import com.aliyun.oss.model.PutObjectRequest;
import com.zbt.common.exception.ErrorCode;
import com.zbt.common.exception.RRException;
import com.zbt.module.file.service.FileService;
import com.zbt.module.file.service.config.ZBTOSSConfigProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

@Slf4j
@Service("ossService")
public class OSSServiceImpl implements FileService {
    private final ZBTOSSConfigProperties properties;

    public OSSServiceImpl(ZBTOSSConfigProperties properties) {
        this.properties = properties;
    }

    private OSS buildOSSClient() {
        return new OSSClient(
                properties.getEndpoint(),
                properties.getAccessKeyId(),
                properties.getSecretAccessKey()
        );
    }

    /**
     * 上传文件
     *
     * @param inputStream
     * @return 访问路径
     */
    @Override
    public String uploadFile(InputStream inputStream, String fileName) {
        OSS ossClient = buildOSSClient();
        try {
            // 创建PutObjectRequest对象。
            PutObjectRequest putObjectRequest = new PutObjectRequest(
                    properties.getBucketName(), fileName, inputStream
            );
            // 创建PutObject请求。
            ossClient.putObject(putObjectRequest);
            return getFileURL(fileName);
        } catch (OSSException oe) {
            oeExceptionHandler(oe);
        } catch (ClientException ce) {
            ceExceptionHandler(ce);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 下载文件
     *
     * @param fileName 文件名称
     * @return
     */
    @Override
    public byte[] downLoadFile(String fileName) {
        OSS ossClient = buildOSSClient();
        try {
            // ossObject包含文件所在的存储空间名称、文件名称、文件元信息以及一个输入流。
            OSSObject ossObject = ossClient.getObject(
                    properties.getBucketName(), fileName
            );

            InputStream inputStream = ossObject.getObjectContent();
            // 将inputStream转为byte Stream
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byte[] buff = new byte[1024];
            int rc = 0;
            while (true) {
                try {
                    if (!((rc = inputStream.read(buff, 0, 1024)) > 0)) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                byteArrayOutputStream.write(buff, 0, rc);
            }

            // ossObject对象使用完毕后必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
            ossObject.close();
            inputStream.close();

            return byteArrayOutputStream.toByteArray();
        } catch (OSSException oe) {
            oeExceptionHandler(oe);
        } catch (ClientException ce) {
            ceExceptionHandler(ce);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 删除文件
     */
    @Override
    public boolean delete(String fileName) {
        OSS ossClient = buildOSSClient();
        try {
            // 删除文件或目录。如果要删除目录，目录必须为空。
            ossClient.deleteObject(
                    properties.getBucketName(), fileName
            );
            return true;
        } catch (OSSException oe) {
            oeExceptionHandler(oe);
        } catch (ClientException ce) {
            ceExceptionHandler(ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return false;
    }

    /**
     * 返回当前bucket下所有图片
     *
     * @return
     */
    @Override
    public List<String> listAllFileInBucket() {
        OSS ossClient = buildOSSClient();
        List<String> urlList = new ArrayList<String>();
        try {
            for (OSSObjectSummary objectSummary : ossClient.listObjects(properties.getBucketName()).getObjectSummaries()) {
                // 获取每张图片的名称, 拼接url地址
                urlList.add(getFileURL(objectSummary.getKey()));
            }
            return urlList;
        } catch (OSSException oe) {
            oeExceptionHandler(oe);
        } catch (ClientException ce) {
            ceExceptionHandler(ce);
        } finally {
            if (ossClient != null) {
                ossClient.shutdown();
            }
        }
        return null;
    }

    /**
     * 获取文件名称
     * todo: 如有需要, 请在业务逻辑中传递不同的回调函数, 以此实现更改文件名获取逻辑
     *
     * @param callable 文件名的获取方式交由调用方实现
     * @return
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

    /**
     * 上传图片
     *
     * @param image
     * @return
     */
    @Override
    public String uploadFile(MultipartFile image, String fileName) {
        if (image == null) {
            return null;
        }
        try {
            // 如果图片大小小于 200KB，则不进行裁剪，直接上传
            InputStream inputStream = image.getInputStream();

            return uploadFile(inputStream, fileName);
        }catch (IOException e) {
            throw new RRException(ErrorCode.FILE_UPLOAD_ERROR);
        }
    }

    /**
     * 处理ce异常
     * @param ce
     */
    private void ceExceptionHandler(ClientException ce) {
        log.info("Caught an ClientException, which means the client encountered "
                + "a serious internal problem while trying to communicate with OSS, "
                + "such as not being able to access the network.");
        log.info("Error Message:" + ce.getMessage());
        throw new RRException(ErrorCode.FILE_UPLOAD_ERROR);
    }

    /**
     * 处理oe异常
     */
    private void oeExceptionHandler(OSSException os) {
        log.info("Caught an OSSException, which means your request made it to OSS, "
                + "but was rejected with an error response for some reason.");
        log.info("Error Message:" + os.getErrorMessage());
        log.info("Error Code:" + os.getErrorCode());
        log.info("Request ID:" + os.getRequestId());
        log.info("Host ID:" + os.getHostId());
        throw new RRException(ErrorCode.FILE_UPLOAD_ERROR);
    }

    /**
     * 返回文件访问url
     * @param fileName 文件名称
     * @return
     */
    private String getFileURL(String fileName) {
        return properties.getBucketEndpoint() + "/" + fileName;
    }
}
