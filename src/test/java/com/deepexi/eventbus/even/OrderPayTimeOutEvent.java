package com.deepexi.eventbus.even;

/**
 * 支付超时事件
 */
public class OrderPayTimeOutEvent {

    private String orderNumber;

    private String productId;


    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProductId() {
        return productId;
    }

    public static final class OrderPayTimeOutEventBuilder {
        private String orderNumber;
        private String productId;

        private OrderPayTimeOutEventBuilder() {
        }

        public static OrderPayTimeOutEventBuilder anOrderPayTimeOutEvent() {
            return new OrderPayTimeOutEventBuilder();
        }

        public OrderPayTimeOutEventBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderPayTimeOutEventBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public OrderPayTimeOutEvent build() {
            OrderPayTimeOutEvent orderPayTimeOutEvent = new OrderPayTimeOutEvent();
            orderPayTimeOutEvent.orderNumber = this.orderNumber;
            orderPayTimeOutEvent.productId = this.productId;
            return orderPayTimeOutEvent;
        }
    }

    @Override
    public String toString() {
        return "OrderPayTimeOutEvent{" +
                "orderNumber='" + orderNumber + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
