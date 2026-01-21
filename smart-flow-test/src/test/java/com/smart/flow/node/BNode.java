package com.smart.flow.node;


import com.smart.flow.TestContext;
import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.execute.BizTaskNode;

/**
 * @Author pw
 * @Date 2025/11/26 16:10
 * @Version 1.0
 **/
@BizNodeAnnotation
public class BNode implements BizTaskNode {
    @Override
    public void process() {
        TestContext contextBean = this.getContextBean();

        System.out.println("执行了BNode节点");

       // throw new TestException("执行了BNode节点error");

    }
}
