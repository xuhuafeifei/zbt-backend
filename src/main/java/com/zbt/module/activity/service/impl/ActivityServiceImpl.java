package com.zbt.module.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.Constant;
import com.zbt.common.utils.PageUtils;
import com.zbt.module.activity.dao.ActivityDao;
import com.zbt.module.activity.dao.OptionDao;
import com.zbt.module.activity.dto.ActivityDto;
import com.zbt.module.activity.dto.OptionDto;
import com.zbt.module.activity.dto.OptionSelectDto;
import com.zbt.module.activity.entity.*;
import com.zbt.module.activity.service.ActivityFileService;
import com.zbt.module.activity.service.ActivityService;
import com.zbt.module.activity.service.OpActService;
import com.zbt.module.file.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.zbt.common.utils.Constant.ActivityOptionType.*;


@Service("activityService")
public class ActivityServiceImpl extends ServiceImpl<ActivityDao, ActivityEntity> implements ActivityService {

    @Resource
    private OptionDao optionDao;

    @Resource
    private ActivityDao activityDao;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        //分页参数
        long curPage = 1;
        long limit = 10;

        if(params.get(Constant.PAGE) != null){
            curPage = Long.parseLong((String)params.get(Constant.PAGE));
        }
        if(params.get(Constant.LIMIT) != null){
            limit = Long.parseLong((String)params.get(Constant.LIMIT));
        }
        long limitNumber = limit;
        long offsetNumber = (curPage - 1) * limitNumber;

        // 获取dto数据
        List<ActivityDto> activityDto = activityDao.getActivityDto(limitNumber, offsetNumber);
        // 处理dto数据
        return handleActivityDto(activityDto, curPage, limit);
    }

    /**
     * 处理dto数据
     * @param activityDto
     * @param curPage
     * @param limit
     * @return
     */
    private PageUtils handleActivityDto(List<ActivityDto> activityDto, long curPage, long limit) {
        // 处理dto
        activityDto.forEach(activity -> {
            // 遍历picturesUrl并根据类型分类
            activity.handleActivityFileEntity();
            // 遍历optionList并根据类型分类
            activity.handleOptionList();
            // 将选项list转化为String
            activity.handleOptionListToString();
        });

        Page<ActivityDto> page = new Page<>(curPage, limit);
        page.setRecords(activityDto);

        return new PageUtils(page);
    }

    @Autowired
    private OpActService opActService;

    /**
     * 条件分页查询
     *
     * @return
     */
    @Override
    public PageUtils queryPageWithCondition(ConditionActivity ca) {
        //分页参数
        long curPage = ca.getPage() == null ? 1 : ca.getPage();
        long limit = ca.getLimit() == null ? 10 : ca.getLimit();

        long limitNumber = limit;
        long offsetNumber = (curPage - 1) * limitNumber;

        // 获取dto数据
        List<ActivityDto> activityDto = activityDao.getActivityDtoWithCondition(limitNumber, offsetNumber, ca);
        // 处理dto
        return handleActivityDto(activityDto, curPage, limit);
    }


    /**
     * 查询所有'活动库'相关的选项
     *
     * @return
     */
    @Override
    public OptionDto listOptions() {
        List<OptionEntity> list = optionDao.selectList(new LambdaQueryWrapper<OptionEntity>()
                .eq(OptionEntity::getLibraryName, "活动库")
                .eq(OptionEntity::getStatus, Constant.OptionState.ENABLE.getValue())
        );
        OptionDto optionDto = new OptionDto();
        list.forEach(e -> {
            String typeName = e.getTypeName();
            String optionName = e.getOptionName();
            if (MATERIAL.getValue().equals(typeName)) {
                optionDto.getMaterialList().add(optionName);
            }else if (USE.getValue().equals(typeName)) {
                optionDto.getUseList().add(optionName);
            }else if (FESTIVAL.getValue().equals(typeName)) {
                optionDto.getFestivalList().add(optionName);
            }else if (TOPIC.getValue().equals(typeName)) {
                optionDto.getTopicList().add(optionName);
            }else if (BRAND.getValue().equals(typeName)) {
                optionDto.getBrandList().add(optionName);
            }
        });
        return optionDto;
    }

    /**
     * 查询所有'活动库' 相关的选项, 并以Select {label: '', value: ''} 形式返回
     *
     * @return
     */
    @Override
    public OptionSelectDto listOptionSelect() {
        List<OptionEntity> list = optionDao.selectList(new LambdaQueryWrapper<OptionEntity>()
                .eq(OptionEntity::getLibraryName, "活动库")
                .eq(OptionEntity::getStatus, Constant.OptionState.ENABLE.getValue())
        );
        OptionSelectDto optionSelectDto = new OptionSelectDto();
        list.forEach(e -> {
            String typeName = e.getTypeName();
            String optionName = e.getOptionName();
            if (MATERIAL.getValue().equals(typeName)) {
                optionSelectDto.getMaterialList().add(new OptionSelectDto.Select(optionName));
            }else if (USE.getValue().equals(typeName)) {
                optionSelectDto.getUseList().add(new OptionSelectDto.Select(optionName));
            }else if (FESTIVAL.getValue().equals(typeName)) {
                optionSelectDto.getFestivalList().add(new OptionSelectDto.Select(optionName));
            }else if (TOPIC.getValue().equals(typeName)) {
                optionSelectDto.getTopicList().add(new OptionSelectDto.Select(optionName));
            }else if (BRAND.getValue().equals(typeName)) {
                optionSelectDto.getBrandList().add(new OptionSelectDto.Select(optionName));
            }
        });
        return optionSelectDto;
    }

    @Autowired
    // todo: 记得修改为自己的逻辑
    @Qualifier("minioService")
    private FileService fileService;

    @Autowired
    private ActivityFileService activityFileService;

    /**
     * 删除素材
     *
     * @param actId
     */
    @Override
    public void delete(Integer actId) {
        // 删除tb_activity
        this.removeById(actId);

        List<ActivityFileEntity> list = activityFileService.list(new LambdaQueryWrapper<ActivityFileEntity>()
                .eq(ActivityFileEntity::getActId, actId)
        );
        // 删除相关文件在tb_activity_file中的存在
        activityFileService.removeByIds(list.stream().map(ActivityFileEntity::getId).collect(Collectors.toList()));
        // 异步删除相关文件在minio中的存储
        new Thread(() -> {
            list.forEach(e -> {
                boolean delete = fileService.delete(e.getUrl());
                System.out.println("文件: " + e.getUrl() + " 删除是否成功: " + delete);
            });
        }).start();
    }

    /**
     * 返回路由
     *
     * @return
     */
    @Override
    public Object getAsyncRoutes() {
        /**
         * json
         */
        return "{\"success\":\"true\",\"data\":[{\"path\":\"/permission\",\"meta\":{\"title\":\"权限管理\",\"icon\":\"lollipop\",\"rank\":10},\"children\":[{\"path\":\"/permission/page/index\",\"name\":\"PermissionPage\",\"meta\":{\"title\":\"页面权限\",\"rank\":0,\"roles\":[\"admin\",\"common\"]}},{\"path\":\"/permission/button/index\",\"name\":\"PermissionButton\",\"meta\":{\"title\":\"按钮权限\",\"rank\":0,\"roles\":[\"admin\",\"common\"],\"auths\":[\"btn_add\",\"btn_edit\",\"btn_delete\"]}}]}]}\n";
    }
}