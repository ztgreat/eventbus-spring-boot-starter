package com.deepexi.eventbus.service.impl;

import com.aliyun.openservices.ons.api.*;
import com.aliyun.openservices.ons.api.order.OrderProducer;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;
import com.aliyun.openservices.ons.api.transaction.TransactionProducer;
import com.deepexi.eventbus.service.RocketMQService;


import javax.annotation.Resource;
import java.util.Date;
import java.util.Properties;

/**
 * @author zhangtao
 * @date 2019/4/12 11:21
 */
public class RocketMQServiceImpl implements RocketMQService {
    /**
     * 配置信息
     */
    @Resource(name = "rocketProperties")
    private Properties properties;
    /**
     * 普通消息、延时消息、定时消息生成者
     */
    @Resource
    private Producer producer;
    /**
     * 顺序消息生产者
     */
    @Resource
    private OrderProducer orderProducer;


    @Override
    public SendResult sendMessage(Message message) {
        return producer.send(message);
    }

    @Override
    public void sendMessage(Message message, SendCallback callback) {
        producer.sendAsync(message, callback);
    }

    @Override
    public void sendOneWayMessage(Message message) {
        producer.sendOneway(message);
    }

    @Override
    public SendResult sendOrderMessage(Message message, String shardingKey) {
        return orderProducer.send(message, shardingKey);
    }

    @Override
    public SendResult sendDelayMessage(Message message, Long delayTimeMillis) {
        message.setStartDeliverTime(System.currentTimeMillis() + delayTimeMillis);
        return producer.send(message);
    }

    @Override
    public SendResult sendDateMessage(Message message, Date date) {
        message.setStartDeliverTime(date.getTime());
        return producer.send(message);
    }

    @Override
    public SendResult sendTransactionMessage(Message message, LocalTransactionChecker checker, LocalTransactionExecuter executer, Object o) {
        TransactionProducer transactionProducer = ONSFactory.createTransactionProducer(this.properties, checker);
        transactionProducer.start();
        return transactionProducer.send(message, executer, o);
    }
}
