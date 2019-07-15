package com.deepexi.eventbus.listener.impl;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.MessageListener;
import com.deepexi.eventbus.listener.RocketMQListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.ManagedMap;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zhangtao
 * @date 2019/4/20 19:11
 */
public class DefaultRocketMQListenerImpl implements RocketMQListener {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultRocketMQListenerImpl.class);
    private static final Map<String, String> TOPIC_CONTAINER = new ConcurrentHashMap<String, String>();
    @Resource
    private Consumer consumer;

    @Override
    public RocketMQListener addListener(String topic, String tag, MessageListener listener) {
        if(TOPIC_CONTAINER.containsKey(topic)) {
            if(LOG.isWarnEnabled()) {
                LOG.warn("不可重复订阅主题：{}", topic);
            }
            return this;
        }
        consumer.subscribe(topic, tag, listener);
        TOPIC_CONTAINER.put(topic, tag);
        if(LOG.isInfoEnabled()) {
            LOG.info("新增订阅信息{} - {}", topic, tag);
        }
        return this;
    }

    @Override
    public void start() {
        if(!consumer.isStarted() && TOPIC_CONTAINER.size() > 0) {
            consumer.start();
            if(LOG.isInfoEnabled()) {
                LOG.info("开始监听消息...");
            }
        }
    }

    @Override
    public void shutdown() {
        if(!consumer.isClosed()) {
            consumer.shutdown();
            if(LOG.isInfoEnabled()) {
                LOG.info("关闭监听消息...");
            }
        }
    }

    @Override
    public Map<String, String> getSubscribeInfo() {
        return new HashMap<String, String>(TOPIC_CONTAINER);
    }

    @Override
    public String getTag(String topic) {
        if(topic == null) {
            return "";
        }
        return TOPIC_CONTAINER.get(topic);
    }

    @Override
    public Set<String> getTopics() {
        return TOPIC_CONTAINER.keySet();
    }

    @Override
    public void cancelListener(String topic) {
        if(topic == null){
            return;
        }
        if(TOPIC_CONTAINER.containsKey(topic)){
            consumer.unsubscribe(topic);
            TOPIC_CONTAINER.remove(topic);
            if(LOG.isInfoEnabled()){
                LOG.info("已移除对主题：{} 的订阅", topic);
            }
        }
        if(LOG.isWarnEnabled()){
            LOG.warn("订阅主题：{}不存在！", topic);
        }
    }
}
