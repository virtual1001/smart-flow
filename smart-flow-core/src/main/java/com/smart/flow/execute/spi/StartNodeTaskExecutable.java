package com.smart.flow.execute.spi;

import com.smart.flow.FlowConstant;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BaseParserModel;
import com.smart.flow.execute.StartParserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author pw
 * @Date 2025/11/25 16:22
 * @Version 1.0
 **/
@Executable(tagName = "start")
@Slf4j
public class StartNodeTaskExecutable implements TaskExecutable {

    //下一个节点ID
    private String nextNodeId;


    @Override
    public String execute(Integer slotIndex) {
        return nextNodeId;
    }

    @Override
    public void initTaskNode(BaseParserModel baseParserModel, String chanId) {
        StartParserModel startParserModel = (StartParserModel) baseParserModel;
        if (ObjectUtils.notEqual(startParserModel.getNodeId(), FlowConstant.START)) {
            throw new FlowEngineException("开始节点的NodeId必须为[start]");

        }
        if (StringUtils.isBlank(startParserModel.getNextId())){
            throw new FlowEngineException("开始节点必须指定下一个节点");
        }
        this.nextNodeId = startParserModel.getNextId();
    }

}
