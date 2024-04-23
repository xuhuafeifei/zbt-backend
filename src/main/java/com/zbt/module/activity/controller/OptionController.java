package com.zbt.module.activity.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zbt.common.exception.ErrorCode;
import com.zbt.common.exception.RRException;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.R;
import com.zbt.common.utils.log.LogAnnotation;
import com.zbt.module.activity.entity.OptionEntity;
import com.zbt.module.activity.service.OptionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Map;



/**
 * 选项表
 *
 * @author xuhuafei
 * @email 2508020102@qq.com
 * @date 2023-12-11 14:47:20
 */
@RestController
@RequestMapping("activity/option")
public class OptionController {
    @Autowired
    private OptionService optionService;

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = optionService.queryPage(params);

        return R.ok().put("data", page);
    }

    /**
     * 查询所有
     */
    @RequestMapping("/listAll")
    public R listAll(@RequestParam String libraryName, String typeName){
        if (StringUtils.isAnyBlank(libraryName, typeName)) {
            throw new RRException("数据接收异常: libraryName = " + libraryName + " typeName = " + typeName
                    , ErrorCode.DATA_ERROR.getErrorCode());
        }
        List<OptionEntity> list = optionService.list(new LambdaQueryWrapper<OptionEntity>()
                .eq(OptionEntity::getLibraryName, libraryName)
                .eq(OptionEntity::getTypeName, typeName)
        );

        return R.ok().put("data", list);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Integer id){
		OptionEntity option = optionService.getById(id);

        return R.ok().put("data", option);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @LogAnnotation(module = "活动库选项模块", operation = "新增原想",
            methodType = LogAnnotation.MethodType.CONTROLLER)
    public R save(@RequestBody OptionEntity option){
        // 重复判断
        if (optionService.count(new QueryWrapper<OptionEntity>()
                .eq("library_name", option.getLibraryName())
                .eq("type_name", option.getTypeName())
                .eq("option_name", option.getOptionName())
        ) >= 1) {
            throw new RRException(ErrorCode.REAPEAT_UPLOAD);
        }
		optionService.save(option);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody OptionEntity option){
        optionService.updateById(option);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Integer[] ids){
		optionService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
