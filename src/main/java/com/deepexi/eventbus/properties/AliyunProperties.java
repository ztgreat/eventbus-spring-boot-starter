package com.deepexi.eventbus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 阿里云账户配置
 *
 * @author zhangtao
 * @date 2019/4/12 16:46
 */
@ConfigurationProperties(prefix = "deepexi.aliyunmq")
public class AliyunProperties {
    /**
     * 阿里云账号AccessKeyId
     */
    private String accessKey;
    /**
     * 阿里云账号AccessKeySecret
     */
    private String secretKey;

    public String getAccessKey() {
        return accessKey;
    }


    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }


    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
