package com.deepexi.eventbus.service;


import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.SendCallback;
import com.aliyun.openservices.ons.api.SendResult;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionChecker;
import com.aliyun.openservices.ons.api.transaction.LocalTransactionExecuter;

import java.util.Date;

/**
 * rocketMQ业务API
 *
 * @author zhangtao
 * @date 2019/4/12 10:53
 */
public interface RocketMQService {

    /**
     * 发送普通消息（可靠同步发送）
     * <p>
     * 同步发送是指消息发送方发出数据后，会在收到接收方发回响应之后才发下一个数据包的通讯方式
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     * @return SendResult
     */
    SendResult sendMessage(Message message);

    /**
     * 发送普通消息（可靠异步发送）
     * <p>
     * 异步发送是指发送方发出数据后，不等接收方发回响应，接着发送下个数据包的通讯方式
     * 消息队列 RocketMQ 的异步发送，需要用户实现异步发送回调接口（SendCallback）
     * 消息发送方在发送了一条消息后，不需要等待服务器响应即可返回，进行第二条消息发送
     * 发送方通过回调接口接收服务器响应，并对响应结果进行处理
     *
     * @param message  消息体（com.aliyun.openservices.ons.api.Message）
     * @param callback 回调接口
     */
    void sendMessage(Message message, SendCallback callback);

    /**
     * 发送普通消息（单向发送）
     * <p>
     * 单向发送特点为发送方只负责发送消息，不等待服务器回应且没有回调函数触发，即只发送请求不等待应答
     * 此方式发送消息的过程耗时非常短，一般在微秒级别
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     */
    void sendOneWayMessage(Message message);

    /**
     * 发送顺序消息
     *
     * @param message     消息体（com.aliyun.openservices.ons.api.Message）
     * @param shardingKey 分区顺序消息中区分不同分区的关键字段
     * @return SendResult
     */
    SendResult sendOrderMessage(Message message, String shardingKey);

    /**
     * 发送延时消息
     *
     * @param message         消息体（com.aliyun.openservices.ons.api.Message）
     * @param delayTimeMillis 时长(单位：毫秒),最大延迟时间为7天
     * @return SendResult
     */
    SendResult sendDelayMessage(Message message, Long delayTimeMillis);

    /**
     * 发送定时消息
     *
     * @param message 消息体（com.aliyun.openservices.ons.api.Message）
     * @param date    指定时间,最大延迟时间为7天
     * @return SendResult
     */
    SendResult sendDateMessage(Message message, Date date);

    /**
     * 发送事务消息
     * <p>
     * 该方法用来发送一条事务型消息. 一条事务型消息发送分为三个步骤:
     * <ol>
     * <li>本服务实现类首先发送一条半消息到到消息服务器;</li>
     * <li>通过<code>executer</code>执行本地事务;</li>
     * <li>根据上一步骤执行结果, 决定发送提交或者回滚第一步发送的半消息;</li>
     * </ol>
     *
     * @param message  消息体（com.aliyun.openservices.ons.api.Message）
     * @param checker  事务检测器，回查本地事务，由Broker回调Producer
     * @param executer 本地事务执行器
     * @param o        应用自定义参数，该参数可以传入本地事务执行器
     * @return SendResult
     */
    SendResult sendTransactionMessage(Message message, LocalTransactionChecker checker, LocalTransactionExecuter executer, Object o);

}
