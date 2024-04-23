package com.zbt.module.activity.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OptionSelectDto {
    @Data
    public static class Select {
        private String value;
        private String label;
        public Select() {}
        public Select(String value) {
            this.value = value;
            this.label = value;
        }
        public Select(String value, String label) {
            this.value = value;
            this.label = label;
        }
    }
    /**
     * 材料选项列表
     */
    private List<Select> materialList;
    /**
     * 用途选项列表
     */
    private List<Select> useList;
    /**
     * 节日选项列表
     */
    private List<Select> festivalList;
    /**
     * 专题选项列表
     */
    private List<Select> topicList;
    /**
     * 品牌选项列表
     */
    private List<Select> brandList;

    public OptionSelectDto() {
        this.materialList = new ArrayList<>();
        this.useList = new ArrayList<>();
        this.festivalList = new ArrayList<>();
        this.topicList = new ArrayList<>();
        this.brandList = new ArrayList<>();
    }
}
