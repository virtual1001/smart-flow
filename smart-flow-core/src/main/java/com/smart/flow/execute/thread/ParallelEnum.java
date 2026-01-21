package com.smart.flow.execute.thread;

/**
 * 并行执行枚举类
 */
public enum ParallelEnum {
    ANY("any", "完成一个任务", AnyParallelExecutor.class),

    ALL("all", "等待所有任务全部完成", AllParallelExecutor.class),

    ;
    private String code;
    private String name;
    private Class<? extends ParallelExecutor> executorClass;
    private ParallelEnum(String code, String name, Class<? extends ParallelExecutor> executorClass) {
        this.code = code;
        this.name = name;
        this.executorClass = executorClass;
    }

    public String getCode() {
        return code;
    }
    public String getName() {
        return name;
    }
    public Class<? extends ParallelExecutor> getExecutorClass() {
        return executorClass;
    }
}
