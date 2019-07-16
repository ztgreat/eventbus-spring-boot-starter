//package com.deepexi.eventbus.factory;
//
//import com.deepexi.eventbus.properties.AsyncExecutorProperties;
//import com.deepexi.eventbus.properties.EventBusProperties;
//import com.google.common.eventbus.AsyncEventBus;
//import com.google.common.eventbus.EventBus;
//import org.apache.log4j.Logger;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.event.ContextRefreshedEvent;
//import org.springframework.context.event.ContextStartedEvent;
//import org.springframework.context.event.EventListener;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import javax.annotation.Resource;
//import java.util.Map;
//import java.util.Set;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.LinkedBlockingDeque;
//import java.util.concurrent.ThreadPoolExecutor;
//import java.util.concurrent.TimeUnit;
//
///**
// * @Author chenglu
// * @Date 2019/7/15
// */
//@Configuration
//@ConditionalOnProperty(value = "deepexi.eventbus.enabled", havingValue = "true")
//public class EventBusCenterFactory implements ApplicationListener<ContextStartedEvent> {
//
//    private static final Logger LOGGER = Logger.getLogger(EventBusCenterFactory.class);
//    @Resource
//    private EventBusProperties eventBusProperties;
//    /**
//     * 同步的eventbus
//     */
//    private EventBus syncEventBus;
//    /**
//     * 异步的eventbus
//     */
//    private EventBus asyncEventBus;
//
//    @Bean
//    public EventBus initEventBus() {
//        this.syncEventBus = new EventBus();
//        AsyncExecutorProperties asyncExecutor = eventBusProperties.getAsyncExecutor();
//        ExecutorService executorService = new ThreadPoolExecutor(asyncExecutor.getCorePoolSize(),
//                asyncExecutor.getMaximumPoolSize(), asyncExecutor.getKeepAliveSecond(), TimeUnit.SECONDS,
//                new LinkedBlockingDeque<Runnable>(asyncExecutor.getQueueSize()));
//        this.asyncEventBus = new AsyncEventBus("asyncDefault", executorService);
//        return asyncEventBus;
//    }
//
//    /**
//     * 注册listener到eventbus
//     */
//    @Override
//    public void onApplicationEvent(ContextStartedEvent contextStartedEvent) {
//        Map<String, Object> beanMap = contextStartedEvent.getApplicationContext().getBeansWithAnnotation(EventListener.class);
//        if (beanMap == null) {
//            LOGGER.info("no EventBus beans were been found");
//            return;
//        }
//        Set<Map.Entry<String, Object>> entryList = beanMap.entrySet();
//        entryList.forEach(entry -> {
//            try{
//                this.asyncEventBus.register(entry.getValue());
//            } catch (Exception e) {
//                LOGGER.warn("add class " + entry.getKey() + " to EventBus failed, will ignore this bean");
//            }
//        });
//    }
//
//    public EventBus getSyncEventBus() {
//        return syncEventBus;
//    }
//
//    public void setSyncEventBus(EventBus syncEventBus) {
//        this.syncEventBus = syncEventBus;
//    }
//
//    public EventBus getAsyncEventBus() {
//        return asyncEventBus;
//    }
//
//    public void setAsyncEventBus(EventBus asyncEventBus) {
//        this.asyncEventBus = asyncEventBus;
//    }
//
//
//}
