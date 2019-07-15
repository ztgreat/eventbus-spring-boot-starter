package com.deepexi.eventbus;

import com.deepexi.eventbus.even.OrderPaySuccessEvent;
import com.deepexi.eventbus.even.OrderPayTimeOutEvent;
import com.deepexi.eventbus.listener.OrderPaySuccessListener;
import com.deepexi.eventbus.listener.OrderPayTimeOutListener;

public class TestEventBus {

    public static void main(String[] args) throws InterruptedException {

        OrderPaySuccessListener successListener = new OrderPaySuccessListener();
        OrderPayTimeOutListener timeOutListener = new OrderPayTimeOutListener();

        EventBusCenter.register(successListener);
        EventBusCenter.register(timeOutListener);
        System.out.println("============   支付成功事件  ====================");

        //支付成功事件
        OrderPaySuccessEvent.OrderPaySuccessEventBuilder successEventBuilder = OrderPaySuccessEvent.OrderPaySuccessEventBuilder.anOrderPaySuccessEvent();
        successEventBuilder.orderNumber("124").productId("3456");

        EventBusCenter.post(successEventBuilder.build());

        System.out.println("============   支付超时事件  ====================");


        //支付超时事件
        OrderPayTimeOutEvent.OrderPayTimeOutEventBuilder timeOutEventBuilder= OrderPayTimeOutEvent.OrderPayTimeOutEventBuilder.anOrderPayTimeOutEvent();
        timeOutEventBuilder.orderNumber("124").productId("3456");

        EventBusCenter.post(timeOutEventBuilder.build());

    }

}
