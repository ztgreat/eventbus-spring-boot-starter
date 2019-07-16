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
    private Boolean enabled;
    /**
     * 异步任务的线程配置
     */
    private AsyncExecutorProperties Executor = new AsyncExecutorProperties();

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public AsyncExecutorProperties getExecutor() {
        return Executor;
    }

    public void setExecutor(AsyncExecutorProperties executor) {
        Executor = executor;
    }
}
