package com.smart.flow.execute.thread;

import java.util.concurrent.ExecutorService;

public interface ExecutorPoolBuild {
    ExecutorService buildPoolExecutor();
}
