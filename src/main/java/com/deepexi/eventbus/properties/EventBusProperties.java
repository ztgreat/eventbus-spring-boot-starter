package com.deepexi.eventbus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @Author chenglu
 * @Date 2019/7/15
 */
@ConfigurationProperties(prefix = "deepexi.eventbus")
public class EventBusProperties {
    /**
     * 是否开启EvenBus
     */
    private Boolean enabled = false;
    private String name = "deepexi-eventbus";

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
