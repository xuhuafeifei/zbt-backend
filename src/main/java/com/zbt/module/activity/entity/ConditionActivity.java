package com.zbt.module.activity.entity;

import com.zbt.common.utils.Constant;
import lombok.Data;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * 查询活动库所需的条件
 */
@Data
public class ConditionActivity {
    private Long page;
    private Long limit;
    private List<String> material;
    private List<String> useCol;
    private List<String> festival;
    private List<String> topic;
    private List<String> brand;

    public Map<String, Object> toParams() {
        HashMap<String, Object> map = new HashMap<>();
        map.put(Constant.PAGE, page);
        map.put(Constant.LIMIT, limit);
        map.put(Constant.MATERIAL, material);
        map.put(Constant.USE_COL, useCol);
        map.put(Constant.FESTIVAL, festival);
        map.put(Constant.TOPIC, topic);
        map.put(Constant.BRAND, brand);
        return map;
    }

    /**
     * 监测数据合法性
     */
    public void dataCheck() {
        if (festival == null) {
            festival = new ArrayList<>();
        }else {
            festival.removeIf(StringUtils::isBlank);
        }
        if (material == null) {
            material = new ArrayList<>();
        }else {
            material.removeIf(StringUtils::isBlank);
        }
        if (brand == null) {
            brand = new ArrayList<>();
        }else {
            brand.removeIf(StringUtils::isBlank);
        }
        if (topic == null) {
            topic = new ArrayList<>();
        }else {
            topic.removeIf(StringUtils::isBlank);
        }
        if (useCol == null) {
            useCol = new ArrayList<>();
        }else {
            useCol.removeIf(StringUtils::isBlank);
        }
    }
}
