package com.deepexi.eventbus.properties;

/**
 * @Author chenglu
 * @Date 2019/7/15
 */
public class AsyncExecutorProperties {
    private int corePoolSize = 10;
    private int maximumPoolSize = 50;
    private long keepAliveSecond = 60;
    private int queueSize = 10240;
    private String poolName = "deepexi-EventBus";

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public long getKeepAliveSecond() {
        return keepAliveSecond;
    }

    public void setKeepAliveSecond(long keepAliveSecond) {
        this.keepAliveSecond = keepAliveSecond;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public void setQueueSize(int queueSize) {
        this.queueSize = queueSize;
    }

    public String getPoolName() {
        return poolName;
    }

    public void setPoolName(String poolName) {
        this.poolName = poolName;
    }

    @Override
    public String toString() {
        return "AsyncExecutorProperties{" +
                "corePoolSize=" + corePoolSize +
                ", maximumPoolSize=" + maximumPoolSize +
                ", keepAliveSecond=" + keepAliveSecond +
                ", queueSize=" + queueSize +
                ", poolName='" + poolName + '\'' +
                '}';
    }
}
