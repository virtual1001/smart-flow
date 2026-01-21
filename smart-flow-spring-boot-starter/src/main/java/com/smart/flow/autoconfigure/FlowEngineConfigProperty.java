package com.smart.flow.autoconfigure;


import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.TimeUnit;

/**
 * @Author pw
 * @Date 2025/10/11 14:21
 * @Version 1.0
 **/
@ConfigurationProperties(prefix = "flow-engine",ignoreUnknownFields = true)
public class FlowEngineConfigProperty {

    private String ruleSource;
    private String scan;
    private Integer currentThreadMaxValue;
    private Boolean enable;

    //异步最大等待时间
    private Integer asyncMaxWaitTime;
    //异步最大等待时间单位
    private TimeUnit asyncMaxWaitTimeUnit;

    //默认全局线程池
    private String defaultExecutorClass;

    //默认线程池最大线程数
    private Integer defaultMaxThreadPoolSize;

    //默认全局线程池核心线程数
    private Integer defaultThreadPoolCorePoolSize;

    //默认全局线程池最大队列数
    private Integer defaultMaxThreadPoolQueueSize;

    public Integer getCurrentThreadMaxValue() {
        return currentThreadMaxValue;
    }

    public void setCurrentThreadMaxValue(Integer currentThreadMaxValue) {
        this.currentThreadMaxValue = currentThreadMaxValue;
    }


    public String getDefaultExecutorClass() {
        return defaultExecutorClass;
    }


    public Integer getDefaultThreadPoolCorePoolSize() {
        return defaultThreadPoolCorePoolSize;
    }


    public Integer getDefaultMaxThreadPoolSize() {
        return defaultMaxThreadPoolSize;
    }

    public Integer getDefaultMaxThreadPoolQueueSize() {
        return defaultMaxThreadPoolQueueSize;
    }

    public String getRuleSource() {
        return ruleSource;
    }

    public void setRuleSource(String ruleSource) {
        this.ruleSource = ruleSource;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Integer getAsyncMaxWaitTime() {
        return asyncMaxWaitTime;
    }

    public TimeUnit getAsyncMaxWaitTimeUnit() {
        return asyncMaxWaitTimeUnit;
    }

    public void setDefaultExecutorClass(String defaultExecutorClass) {
        this.defaultExecutorClass = defaultExecutorClass;
    }


    public void setDefaultThreadPoolCorePoolSize(Integer defaultThreadPoolCorePoolSize) {
        this.defaultThreadPoolCorePoolSize = defaultThreadPoolCorePoolSize;
    }

    public void setDefaultMaxThreadPoolQueueSize(Integer defaultMaxThreadPoolQueueSize) {
        this.defaultMaxThreadPoolQueueSize = defaultMaxThreadPoolQueueSize;
    }

    public void setDefaultMaxThreadPoolSize(Integer defaultMaxThreadPoolSize) {
        this.defaultMaxThreadPoolSize = defaultMaxThreadPoolSize;
    }

    public void setAsyncMaxWaitTime(Integer asyncMaxWaitTime) {
        this.asyncMaxWaitTime = asyncMaxWaitTime;
    }

    public void setAsyncMaxWaitTimeUnit(TimeUnit asyncMaxWaitTimeUnit) {
        this.asyncMaxWaitTimeUnit = asyncMaxWaitTimeUnit;
    }
}
