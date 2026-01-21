package com.smart.flow.execute.thread;

import com.smart.flow.execute.spi.ParallelGatewayNodeTaskExecutable;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * 只需要任意一个任务成功即可
 * @Author pw
 * @Date 2025/12/18 11:09
 * @Version 1.0
 **/
@Slf4j
public class AnyParallelExecutor extends AbstractParallelExecutor{

    @Override
    public void execute(ParallelGatewayNodeTaskExecutable taskExecutable, Integer slotIndex) {
        List<CompletableFuture<FutureResult>> completableFutureList = this.getCompletableFutureList(taskExecutable, slotIndex);
        CompletableFuture<?> voidCompletableFuture = CompletableFuture.anyOf(completableFutureList.toArray(new CompletableFuture[]{}));
        this.extractedResult(voidCompletableFuture, completableFutureList);
    }
}
