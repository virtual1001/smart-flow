package com.smart.flow.property;

import java.util.concurrent.TimeUnit;

/**
 * @Author pw
 * @Date 2025/9/29 17:15
 * @Version 1.0
 **/
public class FlowEngineConfig {
    //流程定义资源地址
    private String ruleSource;
    //路径扫描
    private String scan;

    private Integer currentThreadMaxValue;

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


    public Integer getDefaultMaxThreadPoolSize() {
        return defaultMaxThreadPoolSize;
    }

    public Integer getDefaultMaxThreadPoolQueueSize() {
        return defaultMaxThreadPoolQueueSize;
    }

    public void setDefaultMaxThreadPoolSize(Integer defaultMaxThreadPoolSize) {
        this.defaultMaxThreadPoolSize = defaultMaxThreadPoolSize;
    }

    public void setDefaultMaxThreadPoolQueueSize(Integer defaultMaxThreadPoolQueueSize) {
        this.defaultMaxThreadPoolQueueSize = defaultMaxThreadPoolQueueSize;
    }

    public void setDefaultThreadPoolCorePoolSize(Integer defaultThreadPoolCorePoolSize) {
        this.defaultThreadPoolCorePoolSize = defaultThreadPoolCorePoolSize;
    }

    public void setAsyncMaxWaitTime(Integer asyncMaxWaitTime) {
        this.asyncMaxWaitTime = asyncMaxWaitTime;
    }

    public void setAsyncMaxWaitTimeUnit(TimeUnit asyncMaxWaitTimeUnit) {
        this.asyncMaxWaitTimeUnit = asyncMaxWaitTimeUnit;
    }

    public void setDefaultExecutorClass(String defaultExecutorClass) {
        this.defaultExecutorClass = defaultExecutorClass;
    }

    public Integer getDefaultThreadPoolCorePoolSize() {
        return defaultThreadPoolCorePoolSize;
    }

    public Integer getAsyncMaxWaitTime() {
        return asyncMaxWaitTime;
    }

    public TimeUnit getAsyncMaxWaitTimeUnit() {
        return asyncMaxWaitTimeUnit;
    }

    public String getDefaultExecutorClass() {
        return defaultExecutorClass;
    }

    public String getRuleSource() {
        return ruleSource;
    }

    public void setRuleSource(String ruleSource) {
        this.ruleSource = ruleSource;
    }

    public Integer getCurrentThreadMaxValue() {
        return currentThreadMaxValue;
    }

    public void setCurrentThreadMaxValue(Integer currentThreadMaxValue) {
        this.currentThreadMaxValue = currentThreadMaxValue;
    }

    public String getScan() {
        return scan;
    }

    public void setScan(String scan) {
        this.scan = scan;
    }
}
