package com.zbt.module.activity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zbt.common.utils.R;
import org.springframework.stereotype.Service;

import java.util.*;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zbt.common.utils.PageUtils;
import com.zbt.common.utils.Query;

import com.zbt.module.activity.dao.CommunicateRecordDao;
import com.zbt.module.activity.entity.CommunicateRecordEntity;
import com.zbt.module.activity.service.CommunicateRecordService;


@Service("communicateRecordService")
public class CommunicateRecordServiceImpl extends ServiceImpl<CommunicateRecordDao, CommunicateRecordEntity> implements CommunicateRecordService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CommunicateRecordEntity> page = this.page(
                new Query<CommunicateRecordEntity>().getPage(params),
                new QueryWrapper<CommunicateRecordEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询沟通记录
     *
     * @param orderId
     * @return
     */
    @Override
    public R listAllByOrderId(Integer orderId) {
        List<CommunicateRecordEntity> list = this.list(new LambdaQueryWrapper<CommunicateRecordEntity>()
                .eq(CommunicateRecordEntity::getOrderId, orderId)
                .orderByDesc(CommunicateRecordEntity::getUploadTime)
        );
        // 父id -> [子评论在list中的index...]
        Map<Integer, List<Integer>> map = new HashMap<>();
        List<CommunicateRecordEntity> rootCommentList = new ArrayList<>();
        // 处理评论等级
        for (int i = 0; i < list.size(); i++) {
            Integer commentId = list.get(i).getId();
            Integer pid = list.get(i).getPid();
            // 父评论
            if (pid == null) {
                // 如果map中不包含当前评论id(父id)
                if (!map.containsKey(commentId)) {
                    map.put(commentId, new ArrayList<>());
                }
                // 添加父评论到ans中
                rootCommentList.add(list.get(i));
            }
            // 子评论
            else {
                if (map.containsKey(pid)) {
                    // 设置子评论所在list的idx
                    map.get(pid).add(i);
                }else {
                    // tmp存储 父id->[子评论在list中的index]
                    List<Integer> tmp = new ArrayList<>();
                    tmp.add(i);
                    map.put(pid, tmp);
                }
            }
        }
        // 遍历rootCommentList, 设置父评论的children
        for (CommunicateRecordEntity rootComment : rootCommentList) {
            Integer rootCommentId = rootComment.getId();
            // 子评论idx
            List<Integer> childIdxList = map.get(rootCommentId);
            List<CommunicateRecordEntity> childrenList = new ArrayList<>();
            childIdxList.forEach(childIdx -> childrenList.add(list.get(childIdx)));
            // 添加子评论集合
            rootComment.setChildrenList(childrenList);
        }
        return R.ok().put("data", rootCommentList);
    }
}