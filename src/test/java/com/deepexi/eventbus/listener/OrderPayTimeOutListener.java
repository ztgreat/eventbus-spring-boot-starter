package com.deepexi.eventbus.listener;

import com.deepexi.eventbus.even.OrderPayTimeOutEvent;
import com.google.common.eventbus.Subscribe;

public class OrderPayTimeOutListener {

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     * @param event
     */
    @Subscribe
    public void orderSubscribe(OrderPayTimeOutEvent event) {
        // 订单中心处理其它业务
        System.out.println("处理订单中心的支付超时事件: " + event);
    }

    @Subscribe
    public void memberSubscribe(OrderPayTimeOutEvent event) {
        // 这里可以通过rpc 调用会员中心业务，(不使用MQ)
        System.out.println("处理会员中心的支付超时事件: " + event);
    }

}
