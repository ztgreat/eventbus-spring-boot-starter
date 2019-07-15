package com.deepexi.eventbus.config;

import com.deepexi.eventbus.properties.EventbusProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @Author chenglu
 * @Date 2019/7/15
 */
@Configuration
@EnableConfigurationProperties({EventbusProperties.class})
@ConditionalOnClass({EventbusProperties.class})
public class EventbusAutoConfiguration {
}
