package com.smart.flow;


import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.node.TestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.concurrent.ExecutorService;

/**
 * @Author pw
 * @Date 2025/11/26 14:56
 * @Version 1.0
 **/
@SpringBootTest(classes = TestFlowEngine.class)
@EnableAutoConfiguration
@ComponentScan("com.smart.flow.node")
public class TestFlowEngine {
    @Autowired
    private FlowEngineExecutor flowEngineExecutor;
    @Autowired
    private TestService testService;

    @Test
    public void testFlow() throws InterruptedException {

        ExecutorService executorService = ExecutorPoolHelper.getExecutorPool().buildExecutor("com.smart.flow.execute.thread.FlowGlobalDefaultExecutorBuild");
        for (int i = 1 ;i <=1 ;i++){
            TestContext testContext = new TestContext();
            testContext.setCount(i);
//            Runnable runnable = () -> {
//                flowEngineExecutor.execute("test1",testContext);
//            };
//            Thread thread = new Thread(runnable);
//            thread.setName("thread-test-"+i);
//            thread.start();

            try{
                flowEngineExecutor.execute("test1",testContext);
            }catch (TestException e){
                System.out.println(e.getMessage());
            }catch (FlowEngineException e){
                System.out.println(e.getMessage());
            }

        }

    }
}
