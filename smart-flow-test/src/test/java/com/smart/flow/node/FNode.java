package com.smart.flow.node;

import com.alibaba.fastjson.JSON;
import com.smart.flow.TestContext;
import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.execute.BizTaskNode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author pw
 * @Date 2025/11/27 16:27
 * @Version 1.0
 **/
@BizNodeAnnotation
@Slf4j
public class FNode implements BizTaskNode {
    @Override
    public void process() {

        TestContext contextBean = this.getContextBean();
        try{
            Thread.sleep(5000L);
        }catch (Exception e){

        }
        logger.info("执行了FNode,contextBean:{}", JSON.toJSONString(contextBean));
    }
}
