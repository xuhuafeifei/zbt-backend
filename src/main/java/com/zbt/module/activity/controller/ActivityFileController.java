package com.zbt.module.activity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zbt.common.utils.R;
import com.zbt.common.utils.log.LogAnnotation;
import com.zbt.module.activity.entity.ActivityFileEntity;
import com.zbt.module.activity.service.ActivityFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author xuhuafei
 * @email 2508020102@qq.com
 */
@RestController
@RequestMapping("activity/file")
@Slf4j
public class ActivityFileController {
    @Autowired
    private ActivityFileService activityFileService;

    /*----------waring: 本模块不建议使用@LogAnotation注解, 在处理MultipartFile时, 可能会出现json序列化错误---------------- */

    /**
     * 上传文件
     */
    @RequestMapping("/uploadFile")
    public R uploadFile(@RequestParam("file") MultipartFile file, @RequestParam Integer actId, @RequestParam String fileType) {
        log.info("====================上传文件==================================");
        log.info("actId = " + actId + ", fileType = " + fileType);
        R r = R.ok().put("data", activityFileService.uploadFile(file, actId, fileType));
        log.info("" + r);
        log.info("====================end of data================");
        return r;
    }

    /**
     * 批量上传文件
     */
    @RequestMapping("/uploadFileList")
    public R uploadFileList(@RequestParam("fileList") List<MultipartFile> fileList, @RequestParam Integer actId, @RequestParam String fileType) {
        log.info("====================批量上传文件==================================");
        log.info("actId = " + actId + ", fileType = " + fileType);
        List<ActivityFileEntity> list = activityFileService.uploadFileList(fileList, actId, fileType);
        R r = R.ok().put("data", list);
        log.info("" + r);
        log.info("====================end of data================");
        return r;
    }

    /**
     * 删除文件
     */
    @RequestMapping("/deleteFile")
    @LogAnnotation(module = "文件处理模块", operation = "删除单个文件",
            methodType = LogAnnotation.MethodType.CONTROLLER)
    public R deleteFile(@RequestBody ActivityFileEntity file) {
        activityFileService.delete(file.getId(), file.getUrl());
        return R.ok();
    }

    /**
     * 批量删除文件
     */
    @RequestMapping("/deleteFileList")
    @LogAnnotation(module = "文件处理模块", operation = "批量删除文件",
            methodType = LogAnnotation.MethodType.CONTROLLER)
    public R deleteFileList(@RequestBody List<ActivityFileEntity> list) {
        activityFileService.deleteFileList(list);
        return R.ok();
    }

    /**
     * 传递初稿
     * 并修改接单表中上传信息, 修改order状态为待验收
     */
    @RequestMapping("/firstDraft")
    public R uploadFirstDraft(@RequestParam("file") MultipartFile file,
                              @RequestParam Integer actId,
                              @RequestParam String fileType,
                              @RequestParam Integer orderId) {
        log.info("==================传递初稿======================");
        log.info("actId = " + actId + ", fileType = " + fileType + ", orderId = " + orderId);
        String url = activityFileService.uploadFirstDraft(file, actId, fileType, orderId);
        log.info("==================end======================");
        return R.ok().put("data", url);
    }

    /**
     * 传递源文件
     * 并修改接单表中上传信息, 修改order状态为待验收
     */
    @RequestMapping("/firstSourcefile")
    public R uploadFirstSourcefile(@RequestParam("file") MultipartFile file,
                                   @RequestParam Integer actId,
                                   @RequestParam String fileType,
                                   @RequestParam Integer orderId) {
        log.info("==================传递初稿======================");
        log.info("actId = " + actId + ", fileType = " + fileType + ", orderId = " + orderId);
        activityFileService.uploadFirstSourcefile(file, actId, fileType, orderId);
        log.info("==================end======================");
        return R.ok();
    }

    /**
     * 根据activity_id查询fileType类型的文件数据
     */
    @RequestMapping("/getFileByActId")
    @LogAnnotation(module = "文件模块", operation = "根据act_id, fileType查询文件",
            methodType = LogAnnotation.MethodType.CONTROLLER
    )
    public R getFileByActId(@RequestParam Integer actId, @RequestParam String fileType) {
        List<ActivityFileEntity> list = activityFileService.list(
                new LambdaQueryWrapper<ActivityFileEntity>()
                    .eq(ActivityFileEntity::getActId, actId)
                    .eq(ActivityFileEntity::getType, fileType)
                    .orderByDesc(ActivityFileEntity::getUploadTime)
        );
        return R.ok().put("data", list);
    }

    /**
     * 富文本框图片存储
     */
    @RequestMapping("/fullTextImage")
    public Object uploadFullTextPict(@RequestParam("wangeditor-uploaded-image") MultipartFile file) {
        return activityFileService.uploadFullTextPict(file);
    }
}
