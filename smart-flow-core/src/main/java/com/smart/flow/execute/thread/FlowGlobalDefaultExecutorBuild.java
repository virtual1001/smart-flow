package com.smart.flow.execute.thread;

import com.alibaba.ttl.threadpool.TtlExecutors;
import com.smart.flow.FlowEngineConfigHelper;
import com.smart.flow.property.FlowEngineConfig;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 全局默认执行器构建
 * @Author pw
 * @Date 2025/12/18 14:33
 * @Version 1.0
 **/
public class FlowGlobalDefaultExecutorBuild implements ExecutorPoolBuild {
    @Override
    public ExecutorService buildPoolExecutor() {
        FlowEngineConfig flowEngineConfig = FlowEngineConfigHelper.getFlowEngineConfig();
        String threadName = "flow-thread-";

        return TtlExecutors.getTtlExecutorService(new ThreadPoolExecutor(flowEngineConfig.getDefaultThreadPoolCorePoolSize(), flowEngineConfig.getDefaultMaxThreadPoolSize(), 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(flowEngineConfig.getDefaultMaxThreadPoolQueueSize()), new ThreadFactory() {
            private final AtomicLong number = new AtomicLong();

            @Override
            public Thread newThread(Runnable runnable) {
                Thread newThread = Executors.defaultThreadFactory().newThread(runnable);
                newThread.setName(threadName + number.getAndIncrement());
                newThread.setDaemon(false);
                return newThread;
            }
        }, new ThreadPoolExecutor.CallerRunsPolicy()));

    }
}
