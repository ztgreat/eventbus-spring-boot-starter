package com.deepexi.eventbus.config;

import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.Producer;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.deepexi.eventbus.listener.RocketMQListener;
import com.deepexi.eventbus.listener.impl.DefaultRocketMQListenerImpl;
import com.deepexi.eventbus.properties.AliyunProperties;
import com.deepexi.eventbus.properties.RocketMQProperties;
import com.deepexi.eventbus.service.RocketMQService;
import com.deepexi.eventbus.service.impl.RocketMQServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * RocketMQ自动配置类
 *
 * @author zhangtao
 * @date 2019/4/12 10:52
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class, RocketMQProperties.class})
@ConditionalOnClass({RocketMQService.class, RocketMQListener.class})
public class RocketMQAutoConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(RocketMQAutoConfiguration.class);
    @Resource
    private AliyunProperties aliyunProperties;
    @Resource
    private RocketMQProperties mqProperties;


    /**
     * 配置信息
     *
     * @return Properties
     */
    @Bean(name = "rocketProperties")
    public Properties properties() {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.AccessKey, aliyunProperties.getAccessKey());
        properties.put(PropertyKeyConst.SecretKey, aliyunProperties.getSecretKey());
        properties.setProperty(PropertyKeyConst.SendMsgTimeoutMillis, mqProperties.getTimeoutMillis());
        properties.put(PropertyKeyConst.NAMESRV_ADDR, mqProperties.getNamesrvAddr());
        properties.put(PropertyKeyConst.GROUP_ID, mqProperties.getGroupId());
        properties.put(PropertyKeyConst.MessageModel, mqProperties.getMessageModel().toString().toUpperCase());
        properties.put(PropertyKeyConst.ConsumeThreadNums, mqProperties.getConsumeThreadNums());
        properties.put(PropertyKeyConst.ConsumeTimeout, mqProperties.getConsumeTimeout());
        properties.put(PropertyKeyConst.ConsumeMessageBatchMaxSize, mqProperties.getConsumeMessageBatchMaxSize());
        if(LOG.isInfoEnabled()){
            LOG.info("RocketMQ配置信息:{}", properties);
        }
        return properties;
    }

    /**
     * 普通消息、延时消息、定时消息生成者
     *
     * @return Producer
     */
    @Bean
    @ConditionalOnMissingBean(Producer.class)
    public Producer producer() {
        Producer producer = ONSFactory.createProducer(properties());
        // 只需要启动一次
        if(!producer.isStarted()) {
            producer.start();
        }
        return producer;
    }

    /**
     * 顺序消息生产者
     *
     * @return OrderProducer
     */
    @Bean
    @ConditionalOnMissingBean(OrderProducer.class)
    public OrderProducer orderProducer() {
        OrderProducer orderProducer = ONSFactory.createOrderProducer(properties());
        // 只需要启动一次
        if(!orderProducer.isStarted()) {
            orderProducer.start();
        }
        return orderProducer;
    }

    /**
     * 消费者
     *
     * @return Consumer
     */
    @Bean
    @ConditionalOnMissingBean(Consumer.class)
    public Consumer consumer() {
        return ONSFactory.createConsumer(properties());
    }

    /**
     * 自动配置RocketMQService
     *
     * @return RocketMQService
     */
    @Bean
    @ConditionalOnMissingBean(RocketMQService.class)
    public RocketMQService rocketMQService() {
        RocketMQService mqService = new RocketMQServiceImpl();
        if(LOG.isInfoEnabled()) {
            LOG.info("RocketMQService自动配置成功...");
        }
        return mqService;
    }

    /**
     * 消息监听器
     *
     * @return RocketMQListener
     */
    @Bean
    @ConditionalOnMissingBean(RocketMQListener.class)
    public RocketMQListener rocketMQListener() {
        RocketMQListener rocketMQListener = new DefaultRocketMQListenerImpl();
        if(LOG.isInfoEnabled()) {
            LOG.info("RocketMQListener自动配置成功...");
        }
        return rocketMQListener;
    }

}
