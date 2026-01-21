package com.smart.flow;

import com.google.common.collect.Maps;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.thread.ExecutorPoolBuild;
import com.smart.flow.spi.BeanSwitchSpi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 现成池获取工具
 * @Author pw
 * @Date 2025/12/18 14:15
 * @Version 1.0
 **/
@Slf4j
public class ExecutorPoolHelper {

    private final Map<String, ExecutorService> executorServiceMap ;

    public ExecutorPoolHelper() {
        this.executorServiceMap = Maps.newConcurrentMap();
    }


    public ExecutorService buildExecutor(String threadPoolExecutorClass) {
        //并行网关线程池
        if (StringUtils.isNotBlank(threadPoolExecutorClass)){
                return getExecutorService(threadPoolExecutorClass);
        }
        //默认线程池
        return getExecutorService(FlowEngineConfigHelper.getFlowEngineConfig().getDefaultExecutorClass());
    }

    private ExecutorService getExecutorService(String parallelThreadPoolExecutorClass) {
        try{
            ExecutorService executorService = executorServiceMap.get(parallelThreadPoolExecutorClass);
            if (executorService != null) {
                return executorService;
            }
            Class<ExecutorPoolBuild> aClass = (Class<ExecutorPoolBuild>)Class.forName(parallelThreadPoolExecutorClass);
            ExecutorPoolBuild executorPoolBuild = BeanSwitchSpi.getBeanManage().registerBean(aClass);
            executorService = executorPoolBuild.buildPoolExecutor();
            executorServiceMap.put(parallelThreadPoolExecutorClass,executorService);
            return executorService;

        }catch (Exception e){
            logger.error("获取线程池失败",e);
            throw new FlowEngineException("获取线程池失败:"+e.getMessage());
        }

    }

    public static class ExecutorPool {
        public static final ExecutorPoolHelper EXECUTOR = new ExecutorPoolHelper();
    }

    public static ExecutorPoolHelper getExecutorPool() {
        return ExecutorPool.EXECUTOR;
    }
}
