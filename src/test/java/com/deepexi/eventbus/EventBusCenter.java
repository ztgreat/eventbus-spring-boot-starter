package com.deepexi.eventbus;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import java.util.concurrent.Executors;

public class EventBusCenter {


    private static EventBus eventBus2 = new AsyncEventBus(
            "hello",
            Executors.newFixedThreadPool(4)

    );

    private static EventBus eventBus = new EventBus();
    private EventBusCenter() {
    }

    public static EventBus getInstance() {
        return eventBus;
    }

    public static void register(Object listener) {
        eventBus.register(listener);
    }
    public static void unregister(Object listener) {
        eventBus.unregister(listener);
    }

    public static void post(Object event) {
        eventBus.post(event);
    }

}
