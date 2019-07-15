package com.deepexi.eventbus.listener;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;

import java.util.Map;
import java.util.Set;

/**
 * @author zhangtao
 * @date 2019/4/20 19:10
 */
public interface RocketMQListener {

    /**
     * 监听消息
     *
     * @param topic    消息主题,不可重复订阅主题
     *
     * @param tag      订阅过滤表达式字符串，ONS服务器依据此表达式进行过滤。只支持或运算<br>
     *                 eg: "tag1 || tag2 || tag3"<br>
     *                 如果tag等于null或者*，则表示全部订阅
     *
     * @param listener 消息监听器，Consumer注册消息监听器来订阅消息<br>
     *                 该接口会被{@link Consumer}的多个线程并发调用, 用户需要保证并发安全性<br>
     *                 网络抖动等不稳定的情形可能会带来消息重复，对重复消息敏感的业务可对消息做幂等处理.
     *
     * @return RocketMQListener
     */
    RocketMQListener addListener(String topic, String tag, MessageListener listener);

    /**
     * 开始监听消息
     */
    void start();

    /**
     * 关闭监听
     */
    void shutdown();

    /**
     * 获取所有订阅信息
     *
     * @return Map<String, String>
     */
    Map<String, String> getSubscribeInfo();

    /**
     * 获取某个标签
     *
     * @param topic 主题
     * @return tag
     */
    String getTag(String topic);

    /**
     * 获取所有监听的主题
     *
     * @return Set<String>
     */
    Set<String> getTopics();

    /**
     * 取消对某个主题的监听
     *
     * @param topic 主题
     */
    void cancelListener(String topic);
}
