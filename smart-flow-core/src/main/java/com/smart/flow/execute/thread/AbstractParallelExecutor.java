package com.smart.flow.execute.thread;

import com.google.common.collect.Lists;

import com.smart.flow.ExecutorPoolHelper;
import com.smart.flow.FlowEngineBus;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.Chain;
import com.smart.flow.execute.CommonNodeModel;
import com.smart.flow.execute.spi.ParallelGatewayNodeTaskExecutable;
import com.smart.flow.execute.spi.TaskExecutable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @Author pw
 * @Date 2026/1/9 11:12
 * @Version 1.0
 **/
@Slf4j
public abstract class AbstractParallelExecutor implements ParallelExecutor{


    public List<CompletableFuture<FutureResult>> getCompletableFutureList(ParallelGatewayNodeTaskExecutable taskExecutable, Integer slotIndex) {
        //获取所需的线程池
        ExecutorService executorService = ExecutorPoolHelper.getExecutorPool().buildExecutor(taskExecutable.getParallelThreadPoolExecutorClass());
        List<CompletableFuture<FutureResult>> completableFutureList = Lists.newArrayList();
        for (CommonNodeModel commonNodeModel : taskExecutable.getCommonNodeModelList()){


            Chain chain = FlowEngineBus.getTaskExecutable(taskExecutable.getChanId());
            Map<String, TaskExecutable> taskExecutableMap = chain.getTaskExecutableMap();
            Supplier<FutureResult> supplierTask = () -> {
                try{
                    taskExecutableMap.get(commonNodeModel.getNodeId()).execute(slotIndex);
                    return FutureResult.success();
                }catch (Exception e){
                    return FutureResult.fail(e);
                }
            };
            CompletableFuture<FutureResult> futureResultCompletableFuture = CompletableFuture.supplyAsync(supplierTask, executorService);
            if (futureResultCompletableFuture.isDone()) {
                completableFutureList.add(futureResultCompletableFuture);
                continue;
            }
            TimeOut<FutureResult> futureResultTimeOut = new TimeOut<>(futureResultCompletableFuture, FutureResult.timeout(commonNodeModel.getNodeId()));

            Canceller canceller = new Canceller(Delayer.delay(futureResultTimeOut, taskExecutable.getAsyncMaxWaitTime(), taskExecutable.getAsyncMaxWaitTimeUnit()));
            CompletableFuture<FutureResult> futureResultCompletableFuture1 = futureResultCompletableFuture.whenComplete(canceller);
            completableFutureList.add(futureResultCompletableFuture1);
        }
        return completableFutureList;
    }

    public void extractedResult(CompletableFuture<?> voidCompletableFuture, List<CompletableFuture<FutureResult>> completableFutureList) {
        //处理任务接口
        try{
            voidCompletableFuture.get();
        }catch (InterruptedException | ExecutionException e) {
            logger.error("voidCompletableFuture.get exception", e);
            throw new FlowEngineException(e.getMessage());
        }

        List<FutureResult> futureResultList = completableFutureList.stream().filter(future -> {
            if (future.isDone()) {
                return true;
            } else {
                //先把还未完成的任务取消掉  事实上CompletableFuture并不能cancel掉底层的线程？只能尝试尽可能的取消
                //Thread.sleep()、wait() 等可以中断
                //纯 CPU 死循环（无检查）传统阻塞 I/O（如 FileInputStream.read() 中断不了
                future.cancel(true);
                return false;
            }
        }).map(future -> {
            //处理超时的任务
            try {
                FutureResult futureResult = future.get();
                if (futureResult.isSuccess()) {
                    //超时的任务取消
                    future.cancel(true);
                }
                return futureResult;

            } catch (InterruptedException | ExecutionException e) {

                return FutureResult.fail(e);
            }
        }).collect(Collectors.toList());

        //打印日志或者抛出异常
        for(FutureResult futureResult : futureResultList) {
            if (!futureResult.isSuccess()) {
                Exception exception = futureResult.getException();
                logger.error("futureResult.isSuccess() is false", exception);
                if (exception != null){
                    throw new FlowEngineException(exception.getMessage(),exception);
                }
                throw new FlowEngineException("执行失败");
            }
        }
    }

}
