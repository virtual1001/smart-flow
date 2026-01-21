package com.smart.flow.execute.spi;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;
import com.googlecode.aviator.Expression;

import com.smart.flow.Context;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BaseParserModel;
import com.smart.flow.execute.ExclusiveGatewayParserModel;
import com.smart.flow.execute.FlowDataBus;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * 排他网关节点执行器
 * @Author pw
 * @Date 2025/11/25 14:28
 * @Version 1.0
 **/
@Executable(tagName = "exclusive-gateway")
public class ExclusiveGatewayNodeTaskExecutable implements TaskExecutable {

    private List<ExclusiveGatewayParserModel.ExclusiveGatewayModel> exclusiveGatewayList;


    @Override
    public String execute(Integer slotIndex) {
        //获取当前山下问

        Context currentThreadContext = FlowDataBus.getCurrentThreadContext(slotIndex);
        AviatorEvaluatorInstance aviatorEvaluatorInstance = AviatorEvaluator.getInstance();
        Map<String, Object> params = currentThreadContext.getParams();

        for (ExclusiveGatewayParserModel.ExclusiveGatewayModel exclusiveGateway : exclusiveGatewayList){

            Expression compile = aviatorEvaluatorInstance.compile(exclusiveGateway.getRuleScript());

            Object execute = compile.execute(params);
            if (Boolean.TRUE.equals(execute)) {
                return exclusiveGateway.getNodeId();
            }

        }

        return null;
    }

    @Override
    public void initTaskNode(BaseParserModel baseParserModel, String chanId) {
        ExclusiveGatewayParserModel exclusiveGatewayParserModel = (ExclusiveGatewayParserModel) baseParserModel;
        if (StringUtils.isBlank(exclusiveGatewayParserModel.getNodeId())){
            throw new FlowEngineException("排他网关节点NodeId不能为空");
        }
         this.exclusiveGatewayList = exclusiveGatewayParserModel.getExclusiveGatewayList();
    }
}
