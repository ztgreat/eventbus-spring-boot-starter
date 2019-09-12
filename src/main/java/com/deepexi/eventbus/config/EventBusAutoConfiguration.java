package com.deepexi.eventbus.config;

import cn.hutool.core.thread.ThreadFactoryBuilder;
import com.deepexi.eventbus.AsyncEventBus;
import com.deepexi.eventbus.EventBus;
import com.deepexi.eventbus.annotation.EventBusListener;
import com.deepexi.eventbus.properties.AsyncExecutorProperties;
import com.deepexi.eventbus.properties.EventBusProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author chenglu
 * @date 2019/7/15
 */
@Configuration
@EnableConfigurationProperties({EventBusProperties.class})
@ConditionalOnClass({EventBusProperties.class})
@ConditionalOnProperty(value = "deepexi.eventbus.enabled", havingValue = "true")
public class EventBusAutoConfiguration implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBusAutoConfiguration.class);
    @Resource
    private EventBusProperties eventBusProperties;

    private EventBus eventBus;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        Map<String, Object> beanMap = applicationReadyEvent.getApplicationContext().getBeansWithAnnotation(EventBusListener.class);
        if (beanMap.isEmpty()) {
            LOGGER.info("No EventBus listener bean was found, @EventBusListener on Spring bean to add an EvenBus listener");
            return;
        }
        Set<Map.Entry<String, Object>> entryList = beanMap.entrySet();
        entryList.forEach(entry -> {
            try{
                eventBus.register(entry.getValue());
                LOGGER.info("load Spring bean [" + entry.getKey() + "] to EventBus successfully");
            } catch (Exception e) {
                LOGGER.warn("load Spring bean [" + entry.getKey() + "] to EventBus failed, will ignore this bean");
            }
        });
    }

    @Bean
    public EventBus initEventBus() {
        AsyncExecutorProperties asyncExecutor = eventBusProperties.getExecutor();
        LOGGER.info("init  EventBus bean [eventBus], async threadPool info [{}]", asyncExecutor);
        ExecutorService executorService = new ThreadPoolExecutor(asyncExecutor.getCorePoolSize(),
                asyncExecutor.getMaximumPoolSize(), asyncExecutor.getKeepAliveSecond(), TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(asyncExecutor.getQueueSize()),
                new ThreadFactoryBuilder().setNamePrefix(asyncExecutor.getPoolName()).build());
        this.eventBus = new AsyncEventBus(eventBusProperties.getName(), executorService);
        return eventBus;
    }

}
