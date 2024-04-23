package com.zbt.module.activity.entity.handler;

import com.baomidou.mybatisplus.extension.handlers.JacksonTypeHandler;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * 序列化Json为List<String>
 */
public class StringListTypeHandler extends JacksonTypeHandler {
    private static final ObjectMapper mapper = new ObjectMapper();

    public StringListTypeHandler(Class<Object> type) {
        super(type);
    }

    @Override
    protected Object parse(String json) {

        try {
            return mapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
