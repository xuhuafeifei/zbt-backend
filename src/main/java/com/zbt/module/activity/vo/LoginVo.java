package com.zbt.module.activity.vo;

import lombok.Data;

import java.util.List;

@Data
public class LoginVo {
    private String username;
    private String accessToken;
    private String refreshToken;
    private Number id;
    private String name;
    private List<String> roles;
}