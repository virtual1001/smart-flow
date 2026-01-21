package com.smart.flow.autoconfigure;

import com.smart.flow.property.FlowEngineConfig;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @Author pw
 * @Date 2025/10/23 15:34
 * @Version 1.0
 **/
@Configuration
@EnableConfigurationProperties({FlowEngineConfigProperty.class})
@PropertySource(name = "FlowEngine Default Properties", value = "classpath:/META-INF/flowengine-default.properties")
public class FlowEnginePropertyAutoConfiguration {

    @Bean
    public FlowEngineConfig engineConfig(FlowEngineConfigProperty  flowEngineConfigProperty) {
        FlowEngineConfig flowEngineConfig = new FlowEngineConfig();
        flowEngineConfig.setRuleSource(flowEngineConfigProperty.getRuleSource());
        flowEngineConfig.setScan(flowEngineConfigProperty.getScan());
        flowEngineConfig.setCurrentThreadMaxValue(flowEngineConfigProperty.getCurrentThreadMaxValue());
        flowEngineConfig.setDefaultExecutorClass(flowEngineConfigProperty.getDefaultExecutorClass());
        flowEngineConfig.setDefaultMaxThreadPoolQueueSize(flowEngineConfigProperty.getDefaultMaxThreadPoolQueueSize());
        flowEngineConfig.setDefaultMaxThreadPoolSize(flowEngineConfigProperty.getDefaultMaxThreadPoolSize());
        flowEngineConfig.setDefaultThreadPoolCorePoolSize(flowEngineConfigProperty.getDefaultThreadPoolCorePoolSize());
        flowEngineConfig.setAsyncMaxWaitTime(flowEngineConfigProperty.getAsyncMaxWaitTime());
        flowEngineConfig.setAsyncMaxWaitTimeUnit(flowEngineConfigProperty.getAsyncMaxWaitTimeUnit());
        return  flowEngineConfig;
    }

}
