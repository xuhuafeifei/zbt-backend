package com.zbt.module.activity.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.OptionDao;
import com.zbt.module.activity.entity.OptionEntity;
import com.zbt.module.activity.service.OptionService;


@Service("optionService")
public class OptionServiceImpl extends ServiceImpl<OptionDao, OptionEntity> implements OptionService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OptionEntity> page = this.page(
                new Query<OptionEntity>().getPage(params),
                new QueryWrapper<OptionEntity>()
        );

        return new PageUtils(page);
    }

}