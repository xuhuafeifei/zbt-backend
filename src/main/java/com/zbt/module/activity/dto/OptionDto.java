package com.zbt.module.activity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OptionDto {
    /**
     * 材料选项列表
     */
    private List<String> materialList;
    /**
     * 用途选项列表
     */
    private List<String> useList;
    /**
     * 节日选项列表
     */
    private List<String> festivalList;
    /**
     * 专题选项列表
     */
    private List<String> topicList;
    /**
     * 品牌选项列表
     */
    private List<String> brandList;

    public OptionDto() {
        this.materialList = new ArrayList<>();
        this.useList = new ArrayList<>();
        this.festivalList = new ArrayList<>();
        this.topicList = new ArrayList<>();
        this.brandList = new ArrayList<>();
    }
}
