package com.smart.flow.node;


import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.execute.BizTaskNode;

/**
 * @Author pw
 * @Date 2025/11/27 16:27
 * @Version 1.0
 **/
@BizNodeAnnotation
public class DNode implements BizTaskNode {
    @Override
    public void process() {
        System.out.println("执行了DNode");
    }
}
