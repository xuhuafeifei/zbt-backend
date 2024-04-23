package com.zbt.module.activity.controller;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbt.common.utils.log.LogAnnotation;
import com.zbt.module.activity.dto.ActivityDto;
import com.zbt.module.activity.dto.OptionSelectDto;
import com.zbt.module.activity.entity.ConditionActivity;
import com.zbt.module.activity.entity.OpActEntity;
import com.zbt.module.activity.service.OpActService;
import com.zbt.module.activity.dto.OptionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.zbt.module.activity.entity.ActivityEntity;
import com.zbt.module.activity.service.ActivityService;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;



/**
 * 活动素材表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/activity")
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    /**
     * 查询属于活动库的选项
     */
    @RequestMapping("/listOption")
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "查询属于活动库的选项"
    )
    public R listOptions() {
        OptionDto optionDto = activityService.listOptions();
        return R.ok().put("data", optionDto);
    }

    /**
     * 查询属于活动库的选项, 并以{value: '', label: ''}的形式返回
     * @return
     */
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "查询属于活动库的选项, 并以{value: '', label: ''}的形式返回"
    )
    @RequestMapping("/listOptionSelect")
    public R listOptionSelect() {
        OptionSelectDto optionSelectDto = activityService.listOptionSelect();
        return R.ok().put("data", optionSelectDto);
    }

    /**
     * 列表
     */
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "分页查询活动素材库数据"
    )
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = activityService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 列表
     */
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "分页条件查询活动素材库数据"
    )
    @RequestMapping("/listWithCondition")
    public R listWithCondition(@RequestBody ConditionActivity ca){
        ca.dataCheck();
        PageUtils page = activityService.queryPageWithCondition(ca);

        return R.ok().put("data", page);
    }

    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		ActivityEntity activity = activityService.getById(id);

        return R.ok().put("activity", activity);
    }

    @Autowired
    private OpActService opActService;

    /**
     * 保存activity
     */
    @RequestMapping("/save")
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "保存活动素材库数据"
    )
    public R save(@RequestBody ActivityDto dto){
        // 保存entity
        ActivityEntity entity = dto.toEntity();
        activityService.save(entity);
        // 保存optionList
        List<OpActEntity> opActList = dto.getOptionList(entity.getId());
        opActService.saveBatch(opActList);
        return R.ok().put("data", entity.getId());
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "修改活动素材库数据"
    )
    public R update(@RequestBody ActivityDto dto){
        ActivityEntity entity = dto.toEntity();
        // 修改entity
        activityService.updateById(entity);
        // 删除op_act表中关联option
        opActService.remove(new QueryWrapper<OpActEntity>().eq("act_id", dto.getId()));
        // 重新添加关联option
        List<OpActEntity> optionList = dto.getOptionList(entity.getId());
        opActService.saveBatch(optionList);
        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @LogAnnotation(methodType = LogAnnotation.MethodType.CONTROLLER, module = "活动素材库",
            operation = "删除活动素材库数据"
    )
    public R delete(@RequestParam Integer actId){
        activityService.delete(actId);

        return R.ok();
    }

    /**
     * waring: 该接口不要修改, 否则前端路由菜单无法显示
     * @Target 接口通过取代前端mock返回动态路由的功能, 解决wangeditor线上无法上传图片的bug
     *
     * 该功能原本是由前端mock解决, 但mock功能于wangeditor(富文本框架)的图片上传功能冲突
     * mock会覆盖wangeditor在线上环境下, 图片的上传功能. 因此路由功能由后端取代, 前端关闭
     * mock
     * 返回路由
     *
     * 取代前端的mock路由, 解决wangeditor无法在前端项目中, 线上环境无法上传图片的问题
     */
    @RequestMapping("/getAsyncRoutes")
    public Object getAsyncRoutes() {
        return activityService.getAsyncRoutes();
    }
}
