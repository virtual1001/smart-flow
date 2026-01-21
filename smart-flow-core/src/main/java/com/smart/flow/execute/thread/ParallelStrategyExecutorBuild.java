package com.smart.flow.execute.thread;

import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.spi.BeanSwitchSpi;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * 并行策略执行器构建类
 * @Author pw
 * @Date 2025/12/19 16:14
 * @Version 1.0
 **/
@Slf4j
public class ParallelStrategyExecutorBuild {

    private Map<String, ParallelExecutor> executorMap = new HashMap<>();
    private static class Builder {
        private static final ParallelStrategyExecutorBuild EXECUTOR = new ParallelStrategyExecutorBuild();
    }

    public static ParallelStrategyExecutorBuild builder() {
        return Builder.EXECUTOR;
    }

    public ParallelExecutor getParallelExecutor(ParallelEnum parallelEnum) {
        return executorMap.get(parallelEnum.getCode());
    }

    public void init () {
        for (ParallelEnum parallelEnum : ParallelEnum.values()) {
            try{
                Class<ParallelExecutor> aClass = (Class<ParallelExecutor>)Class.forName(parallelEnum.getExecutorClass().getName());
                ParallelExecutor parallelExecutor = BeanSwitchSpi.getBeanManage().registerBean(aClass);
                executorMap.put(parallelEnum.getCode(), parallelExecutor);
            }catch (Exception e) {
                logger.error(e.getMessage(),e);
                throw new FlowEngineException(e.getMessage());
            }
        }
    }
}
