package com.smart.flow.execute.spi;

import com.smart.flow.FlowConstant;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BaseParserModel;
import com.smart.flow.execute.EndParserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;

/**
 * @Author pw
 * @Date 2025/11/25 16:22
 * @Version 1.0
 **/
@Executable(tagName = "end")
@Slf4j
public class EndNodeTaskExecutable implements TaskExecutable {

    //下一个节点ID
    private String nextNodeId;

    @Override
    public String execute(Integer slotIndex) {
        return nextNodeId;
    }

    @Override
    public void initTaskNode(BaseParserModel baseParserModel, String chanId) {
        EndParserModel endParserModel = (EndParserModel) baseParserModel;
        if (ObjectUtils.notEqual(endParserModel.getNodeId(), FlowConstant.END)) {
            throw new FlowEngineException("结束节点的NodeId必须为[end]");

        }
        this.nextNodeId = endParserModel.getNextId();

    }
}
