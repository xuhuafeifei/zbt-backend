package com.zbt.module.file.service.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "oss")
public class ZBTOSSConfigProperties {
    private String bucketName;
    private String accessKeyId;
    private String secretAccessKey;
    private String endpoint;
    private String bucketEndpoint;
}
