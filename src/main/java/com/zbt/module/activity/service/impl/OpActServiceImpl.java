package com.zbt.module.activity.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.OpActDao;
import com.zbt.module.activity.entity.OpActEntity;
import com.zbt.module.activity.service.OpActService;


@Service("opActService")
public class OpActServiceImpl extends ServiceImpl<OpActDao, OpActEntity> implements OpActService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OpActEntity> page = this.page(
                new Query<OpActEntity>().getPage(params),
                new QueryWrapper<OpActEntity>()
        );

        return new PageUtils(page);
    }

}