package com.smart.flow.execute.spi;


import com.smart.flow.FlowEngineBus;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.AbstractTaskExecutable;
import com.smart.flow.execute.BizTaskNode;
import com.smart.flow.execute.CommonNodeModel;
import org.apache.commons.lang3.ObjectUtils;

/**
 *
 * @Author pw
 * @Date 2025/11/24 19:02
 * @Version 1.0
 **/
@Executable(tagName = "common-node")
public class CommonNodeTaskExecutable extends AbstractTaskExecutable {

    //下一个节点ID
    private String nextNodeId;
    //执行节点
    private BizTaskNode bizTaskNode;


    @Override
    public void initBizTaskNode(CommonNodeModel commonNodeModel) {

        this.nextNodeId = commonNodeModel.getNextId();
        BizTaskNode taskNode = FlowEngineBus.getTaskNode(commonNodeModel.getNodeId());
        if (ObjectUtils.isEmpty(taskNode)){
            throw new FlowEngineException("nodeId:"+commonNodeModel.getNodeId()+" not found bean");
        }
        this.bizTaskNode = taskNode;
    }

    @Override
    public String executeBiz(Integer index) {
        bizTaskNode.process();
        return nextNodeId;
    }
}
