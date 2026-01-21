package com.smart.flow.execute;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.smart.flow.execute.spi.TaskExecutable;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author pw
 * @Date 2025/11/21 19:10
 * @Version 1.0
 **/

public interface BizTaskNode {
    TransmittableThreadLocal<AbstractTaskExecutable> BIZ_THREAD_LOCAL = new TransmittableThreadLocal<>();

    void process();


    default <T> T getContextBean() {
        Integer index = BIZ_THREAD_LOCAL.get().getIndex();
        return (T) FlowDataBus.getCurrentThreadContext(index);
    }

    default void setTaskExecutable(AbstractTaskExecutable taskExecutable) {
        if (taskExecutable == null) {
            return;
        }
        TaskExecutable compareExecutable = BIZ_THREAD_LOCAL.get();
        if (compareExecutable == null) {
            this.BIZ_THREAD_LOCAL.set(taskExecutable);
        } else {
            if (ObjectUtils.notEqual(taskExecutable, compareExecutable)) {
                this.BIZ_THREAD_LOCAL.set(taskExecutable);
            }
        }
    }

    default void removeTaskExecutable() {
        TaskExecutable compareExecutable = BIZ_THREAD_LOCAL.get();
        if (compareExecutable != null) {
            BIZ_THREAD_LOCAL.remove();
        }
    }
}
