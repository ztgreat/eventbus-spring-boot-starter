package com.deepexi.eventbus.even;

/**
 * 支付成功事件
 */
public class OrderPaySuccessEvent {

    private String orderNumber;

    private String productId;

    public String getOrderNumber() {
        return orderNumber;
    }

    public String getProductId() {
        return productId;
    }

    public static final class OrderPaySuccessEventBuilder {
        private String orderNumber;
        private String productId;

        private OrderPaySuccessEventBuilder() {
        }

        public static OrderPaySuccessEventBuilder anOrderPaySuccessEvent() {
            return new OrderPaySuccessEventBuilder();
        }

        public OrderPaySuccessEventBuilder orderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
            return this;
        }

        public OrderPaySuccessEventBuilder productId(String productId) {
            this.productId = productId;
            return this;
        }

        public OrderPaySuccessEvent build() {
            OrderPaySuccessEvent orderPaySuccessEvent = new OrderPaySuccessEvent();
            orderPaySuccessEvent.orderNumber = this.orderNumber;
            orderPaySuccessEvent.productId = this.productId;
            return orderPaySuccessEvent;
        }
    }

    @Override
    public String toString() {
        return "OrderPaySuccessEvent{" +
                "orderNumber='" + orderNumber + '\'' +
                ", productId='" + productId + '\'' +
                '}';
    }
}
