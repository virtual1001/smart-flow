package com.smart.flow.node;


import com.smart.flow.TestContext;
import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.execute.BizTaskNode;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Author pw
 * @Date 2025/11/26 16:10
 * @Version 1.0
 **/
@BizNodeAnnotation
public class ANode implements BizTaskNode {

    @Autowired
    private TestService testService;
    public TestService getTestService() {
        return testService;
    }
    @Override
    public void process() {
//        System.out.println("执行了ANode节点");
//       TestContext contextBean = (TestContext) this.getContextBean();
//        contextBean.setName("测试上下文参数");
//        contextBean.getParams().put("test", "test");
//        System.out.println(JSON.toJSONString(contextBean));
//        testService.testFlow();
        TestContext contextBean = (TestContext) this.getContextBean();
        contextBean.getParams().put("test", "test");
        System.out.println("执行了ANode节点");


    }
}
