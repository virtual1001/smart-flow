package com.smart.flow.node;


import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.execute.BizTaskNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author pw
 * @Date 2025/11/27 16:27
 * @Version 1.0
 **/
@BizNodeAnnotation(nodeId = {"jNode","jJNode"})
@Slf4j
public class JNode implements BizTaskNode {
    @Override
    public void process() {
        logger.info("执行了JNode");
    }
}
