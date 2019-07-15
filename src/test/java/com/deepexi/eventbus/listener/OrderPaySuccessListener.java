package com.deepexi.eventbus.listener;

import com.deepexi.eventbus.even.OrderPaySuccessEvent;
import com.google.common.eventbus.AllowConcurrentEvents;
import com.google.common.eventbus.Subscribe;

/**
 * 订单支付成功监听者
 */
public class OrderPaySuccessListener {

    /**
     * 只有通过@Subscribe注解的方法才会被注册进EventBus
     * 而且方法有且只能有1个参数
     * @param event
     */
    @Subscribe
    @AllowConcurrentEvents
    public void orderSubscribe(OrderPaySuccessEvent event) {
        // 订单中心处理其它业务
        try {
            Thread.sleep(5000);
            System.out.println("处理订单中心的支付成功事件: " + event);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

//    @Subscribe
//    public void object(Object event) {
//        // 订单中心处理其它业务
//        System.out.println("测试object: " + event);
//    }



    @Subscribe
    public void memberSubscribe(OrderPaySuccessEvent event) {
        // 这里可以通过rpc 调用会员中心业务
        System.out.println("处理会员中心的支付成功事件: " + event);
    }

}
