package com.smart.flow;

import com.smart.flow.execute.thread.ParallelStrategyExecutorBuild;
import org.springframework.beans.factory.SmartInitializingSingleton;

/**
 * @Author pw
 * @Date 2025/11/21 16:55
 * @Version 1.0
 **/
public class FlowEngineExecutorStart implements SmartInitializingSingleton {
    private final FlowEngineExecutor flowEngineExecutor;

    public FlowEngineExecutorStart(FlowEngineExecutor flowEngineExecutor) {
        this.flowEngineExecutor = flowEngineExecutor;
    }
    @Override
    public void afterSingletonsInstantiated() {
        flowEngineExecutor.init();
        //初始化并行策略
        ParallelStrategyExecutorBuild.builder().init();

    }
}
