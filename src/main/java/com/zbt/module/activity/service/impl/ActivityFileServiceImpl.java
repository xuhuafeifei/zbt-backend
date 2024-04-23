package com.zbt.module.activity.service.impl;

import com.zbt.common.exception.RRException;
import com.zbt.common.utils.Constant;
import com.zbt.module.activity.dao.ActivityFileDao;
import com.zbt.module.activity.entity.ActivityFileEntity;
import com.zbt.module.activity.entity.OrderEntity;
import com.zbt.module.activity.entity.ReceOrderEntity;
import com.zbt.module.activity.service.ActivityFileService;
import com.zbt.module.activity.service.OrderService;
import com.zbt.module.activity.service.ReceOrderService;
import com.zbt.module.file.service.FileService;
import com.zbt.module.file.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;
import org.springframework.web.multipart.MultipartFile;


@Service("ActivityFileService")
@Slf4j
public class ActivityFileServiceImpl extends ServiceImpl<ActivityFileDao, ActivityFileEntity> implements ActivityFileService {
    @Autowired
    // todo: 请根据具体的文件存储逻辑, 注入不同的fileService
    @Qualifier("ossService")
    private FileService fileService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ActivityFileEntity> page = this.page(
                new Query<ActivityFileEntity>().getPage(params),
                new QueryWrapper<ActivityFileEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 创建存储图片/源文件对象
     * @param fileType
     * @param actId
     * @return
     */
    private ActivityFileEntity createActivityFile(String url, String fileType, Integer actId) {
        ActivityFileEntity entity = new ActivityFileEntity();
        entity.setActId(actId);
        entity.setUrl(url);
        entity.setType(fileType);
        entity.setUploadTime(LocalDateTime.now());
        return entity;
    }

    /**
     * 上传图片至minio, 并将图片信息存储到tb_activity_file中
     *
     * @param file
     * @param actId
     * @param fileType
     * @return
     */
    @Override
    public ActivityFileEntity uploadFile(MultipartFile file, Integer actId, String fileType) {
        // 获取文件url
        String url = fileService.uploadFile(file,
                fileService.getFileName(this.getFileName(file.getOriginalFilename()))
        );
        // 存储图片
        ActivityFileEntity activityFileEntity = createActivityFile(url, fileType, actId);
        log.info("activityFileEntity: " + activityFileEntity);
        this.save(activityFileEntity);
        return activityFileEntity;
    }

    /**
     * 批量上传图片至minio, 并将图片信息存储到tb_activity_file中
     *
     * @param fileList
     * @param actId
     * @param fileType
     * @return
     */
    @Override
    public List<ActivityFileEntity> uploadFileList(List<MultipartFile> fileList, Integer actId, String fileType) {
        List<ActivityFileEntity> list = new ArrayList<>();
        fileList.forEach(e -> {
            // 获取文件url
            String url = fileService.uploadFile(e,
                    fileService.getFileName(this.getFileName(e.getOriginalFilename()))
            );
            // 存储图片
            ActivityFileEntity activityFileEntity = createActivityFile(url, fileType, actId);
            log.info("activityFileEntity: " + activityFileEntity);
            ActivityFileEntity entity = createActivityFile(url, fileType, actId);
            list.add(entity);
        });
        this.saveBatch(list);
        return list;
    }

    /**
     * 删除minio中存储的图片, 同时删除tb_activity_file中的信息
     *  @param id
     * @param url
     * @return
     */
    @Override
    @Deprecated
    public boolean delete(Integer id, String url) {
        fileService.delete(url);
        // 删除db中的数据
        boolean b = this.removeById(id);
        if (!b) {
            throw new RRException("文件删除异常");
        }
        return true;
    }

    /**
     * 批量删除minio中存储的图片, 同时删除tb_activity_file中的信息
     *
     * @param list
     */
    @Override
    @Deprecated
    public void deleteFileList(List<ActivityFileEntity> list) {
        list.forEach(e -> fileService.delete(e.getUrl()));
    }

    /**
     * todo: 如果需要更换当前service的文件名获取逻辑, 请修改该方法
     * @param fileName
     * @return
     */
    private Callable<String> getFileName(String fileName) {
//        return FileUtils.getRandomFileName(fileName);
        return FileUtils.getOriginFileNameWithDate(fileName);
    }

    @Autowired
    private ReceOrderService receOrderService;

    @Autowired
    private OrderService orderService;

    /**
     * 传递初稿
     * 并修改接单表中上传信息
     *  @param file
     * @param actId
     * @param fileType
     * @return
     */
    @Override
    public String uploadFirstDraft(MultipartFile file, Integer actId, String fileType, Integer orderId) {
        String url = this.uploadFile(file, actId, fileType).getUrl();
        ReceOrderEntity receOrderEntity = new ReceOrderEntity();
        receOrderEntity.setId(orderId);
        receOrderEntity.setFirstDraftTime(LocalDateTime.now());
        receOrderService.updateById(receOrderEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(Constant.OrderStatus.WAITING_FOR_ACCEPT.getValue());
        orderService.updateById(orderEntity);
        return url;
    }

    /**
     * 传递源文件
     * 并修改接单表中上传信息
     *
     * @param file
     * @param actId
     * @param fileType
     * @param orderId
     */
    @Override
    public void uploadFirstSourcefile(MultipartFile file, Integer actId, String fileType, Integer orderId) {
        this.uploadFile(file, actId, fileType);
        ReceOrderEntity receOrderEntity = new ReceOrderEntity();
        receOrderEntity.setId(orderId);
        receOrderEntity.setFirstSourcefileTime(LocalDateTime.now());
        receOrderService.updateById(receOrderEntity);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(orderId);
        orderEntity.setStatus(Constant.OrderStatus.WAITING_FOR_ACCEPT.getValue());
        orderService.updateById(orderEntity);
    }

    /**
     * 接收富文本框传递的图片
     *
     * @param file
     * @return
     */
    @Override
    public Object uploadFullTextPict(MultipartFile file) {
        class Success {
            public final int errno;
            public final Object data;
            public Success(String url) {
                this.errno = 0;
                HashMap<String, String> map = new HashMap<>();
                map.put("url", url);
                this.data = map;
            }
        }
        class Error {
            public final int errno;
            public final String message;
            public Error() {
                this.errno = 1;
                this.message = "图片/视频上传失败";
            }
        }

        String url = fileService.uploadFile(file, fileService.getFileName(
                FileUtils.getRandomFileName(file.getOriginalFilename()))
        );
        if (url == null) {
            // 上传失败
            return new Error();
        }else {
            return new Success(url);
        }
    }
}