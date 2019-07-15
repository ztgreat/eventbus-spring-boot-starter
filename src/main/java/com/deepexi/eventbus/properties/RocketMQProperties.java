package com.deepexi.eventbus.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * rocketMQ配置
 *
 * @author zhangtao
 * @date 2019/4/12 10:51
 */
@ConfigurationProperties(prefix = "deepexi.aliyunmq.rocketmq")
public class RocketMQProperties {
    /**
     * 消息队列实例TCP接入点
     */
    private String namesrvAddr;
    /**
     * 发送超时时间，单位毫秒
     */
    private String timeoutMillis = "3000";
    /**
     * 消息组ID
     */
    private String groupId;
    /**
     * 订阅方式：CLUSTERING-集群订阅；BROADCASTING-广播订阅
     */
    private MeesageModel messageModel = MeesageModel.CLUSTERING;
    /**
     * 消费线程数量
     */
    private String consumeThreadNums = "64";
    /**
     * 每条消息消费的最大超时时间,超过这个时间,这条消息将会被视为消费失败,等下次重新投递再次消费,每个业务需要设置一个合理的值,单位(分钟)
     */
    private String consumeTimeout = "15";
    /**
     * 每次批量消费的最大消息数量,允许自定义范围为[1, 32],实际消费数量可能小于该值.
     */
    private String consumeMessageBatchMaxSize = "1";

    /**
     * 消息订阅方式
     */
    public enum MeesageModel{
        /**
         * 集群订阅
         */
        CLUSTERING,
        /**
         * 广播订阅
         */
        BROADCASTING
    }

    public MeesageModel getMessageModel() {
        return messageModel;
    }

    public void setMessageModel(MeesageModel messageModel) {
        this.messageModel = messageModel;
    }

    public String getTimeoutMillis() {
        return timeoutMillis;
    }


    public void setTimeoutMillis(String timeoutMillis) {
        this.timeoutMillis = timeoutMillis;
    }


    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }


    public String getNamesrvAddr() {
        return namesrvAddr;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public String getConsumeThreadNums() {
        return consumeThreadNums;
    }

    public void setConsumeThreadNums(String consumeThreadNums) {
        this.consumeThreadNums = consumeThreadNums;
    }

    public String getConsumeTimeout() {
        return consumeTimeout;
    }

    public void setConsumeTimeout(String consumeTimeout) {
        this.consumeTimeout = consumeTimeout;
    }

    public String getConsumeMessageBatchMaxSize() {
        return consumeMessageBatchMaxSize;
    }

    public void setConsumeMessageBatchMaxSize(String consumeMessageBatchMaxSize) {
        this.consumeMessageBatchMaxSize = consumeMessageBatchMaxSize;
    }

}
