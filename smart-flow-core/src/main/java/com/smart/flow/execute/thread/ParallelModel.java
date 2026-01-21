package com.smart.flow.execute.thread;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author pw
 * @Date 2025/12/18 11:42
 * @Version 1.0
 **/
public class ParallelModel {

    //并行执行枚举类
    private ParallelEnum parallelEnum;
    //异步最大等待时间
    private Integer asyncMaxWaitTime;
    //异步最大等待时间单位
    private TimeUnit asyncMaxWaitTimeUnit;
    //并行网关级别线程池
    private String parallelThreadPoolExecutorClass ;
    //并行节点信息
    private List<ParallelNodeInfo> parallelNodeInfoList;

    public void setParallelThreadPoolExecutorClass(String parallelThreadPoolExecutorClass) {
        this.parallelThreadPoolExecutorClass = parallelThreadPoolExecutorClass;
    }

    public void setParallelEnum(ParallelEnum parallelEnum) {
        this.parallelEnum = parallelEnum;
    }

    public void setAsyncMaxWaitTime(Integer asyncMaxWaitTime) {
        this.asyncMaxWaitTime = asyncMaxWaitTime;
    }

    public void setAsyncMaxWaitTimeUnit(TimeUnit asyncMaxWaitTimeUnit) {
        this.asyncMaxWaitTimeUnit = asyncMaxWaitTimeUnit;
    }

    public void setParallelNodeInfoList(List<ParallelNodeInfo> parallelNodeInfoList) {
        this.parallelNodeInfoList = parallelNodeInfoList;
    }

    public ParallelEnum getParallelEnum() {
        return parallelEnum;
    }

    public String getParallelThreadPoolExecutorClass() {
        return parallelThreadPoolExecutorClass;
    }

    public Integer getAsyncMaxWaitTime() {
        return asyncMaxWaitTime;
    }

    public TimeUnit getAsyncMaxWaitTimeUnit() {
        return asyncMaxWaitTimeUnit;
    }

    public List<ParallelNodeInfo> getParallelNodeInfoList() {
        return parallelNodeInfoList;
    }

    public static class ParallelNodeInfo{
        private String tagName;
        private String nodeId;
        private String name;
        private String description;


        public String getTagName() {
            return tagName;
        }

        public String getNodeId() {
            return nodeId;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setTagName(String tagName) {
            this.tagName = tagName;
        }
    }

}
