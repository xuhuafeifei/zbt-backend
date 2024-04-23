package com.zbt.module.activity.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.ReceOrderDao;
import com.zbt.module.activity.entity.ReceOrderEntity;
import com.zbt.module.activity.service.ReceOrderService;


@Service("receOrderService")
public class ReceOrderServiceImpl extends ServiceImpl<ReceOrderDao, ReceOrderEntity> implements ReceOrderService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<ReceOrderEntity> page = this.page(
                new Query<ReceOrderEntity>().getPage(params),
                new QueryWrapper<ReceOrderEntity>()
        );

        return new PageUtils(page);
    }

}