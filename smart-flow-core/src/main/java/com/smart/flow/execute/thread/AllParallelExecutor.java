package com.smart.flow.execute.thread;

import com.smart.flow.execute.spi.ParallelGatewayNodeTaskExecutable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 需要全部完成任务
 * @Author pw
 * @Date 2025/12/18 13:43
 * @Version 1.0
 **/

public class AllParallelExecutor extends AbstractParallelExecutor{
    private static final Logger log = LoggerFactory.getLogger(AllParallelExecutor.class);

    @Override
    public void execute(ParallelGatewayNodeTaskExecutable taskExecutable, Integer slotIndex) {
        List<CompletableFuture<FutureResult>> completableFutureList = getCompletableFutureList(taskExecutable, slotIndex);
        //合并任务汇总，并不阻塞主线程
        CompletableFuture<?> voidCompletableFuture = CompletableFuture.allOf(completableFutureList.toArray(new CompletableFuture[]{}));
        //处理结果
        this.extractedResult(voidCompletableFuture, completableFutureList);
    }

}
