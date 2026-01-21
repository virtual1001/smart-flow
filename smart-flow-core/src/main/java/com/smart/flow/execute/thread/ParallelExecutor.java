package com.smart.flow.execute.thread;


import com.smart.flow.execute.spi.ParallelGatewayNodeTaskExecutable;

import java.util.concurrent.*;
import java.util.function.BiConsumer;

/**
 * @Author pw
 * @Date 2025/12/18 11:10
 * @Version 1.0
 **/
public interface ParallelExecutor {
    void execute(ParallelGatewayNodeTaskExecutable taskExecutable , Integer slotIndex);


     class Canceller implements BiConsumer<FutureResult, Throwable> {
        private Future<?> future;

        public Canceller(Future<?> future) {
            this.future = future;
        }


         @Override
         public void accept(FutureResult futureResult, Throwable throwable) {
            // 取消任务超时任务
             if (null == throwable &&  future != null && !future.isDone()){
                 future.cancel(false);
             }
         }
     }


     class Delayer {
         private static ScheduledThreadPoolExecutor delayer ;

         public static ScheduledFuture<?> delay(Runnable runnable , long delay, TimeUnit timeUnit){
             return delayer.schedule(runnable, delay, timeUnit);
         }


         // 创建一个后台守护线程
         static final class DaemonThreadFactory implements ThreadFactory {

             @Override
             public Thread newThread(Runnable runnable) {
                 Thread thread = new Thread(runnable);
                 thread.setDaemon(true);
                 thread.setName("DelayScheduler");
                 return thread;
             }
         }

         static {
             delayer = new ScheduledThreadPoolExecutor(1, new DaemonThreadFactory());
             // 设置取消任务时是否移除任务 非常重量默认是false;不设置会导致非堆内存暴涨 出现oom
             delayer.setRemoveOnCancelPolicy(true);
         }



     }

     class TimeOut<T> implements Runnable{
         final CompletableFuture<T> completableFuture;
         final T futureResult;

         public TimeOut(CompletableFuture<T> completableFuture, T futureResult) {
             this.completableFuture = completableFuture;
             this.futureResult = futureResult;
         }

         @Override
         public void run() {
             if (null != completableFuture && !completableFuture.isDone()) {
                 //如果已经超时 就让任务直接完成
                 completableFuture.complete(futureResult);
             }

         }
     }

}
