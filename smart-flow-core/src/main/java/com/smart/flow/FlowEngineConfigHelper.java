package com.smart.flow;


import com.smart.flow.property.FlowEngineConfig;
import com.smart.flow.spi.BeanSwitchSpi;

/**
 * @Author pw
 * @Date 2025/11/26 13:21
 * @Version 1.0
 **/
public class FlowEngineConfigHelper {
    private static FlowEngineConfig flowEngineConfig = null;
    public static FlowEngineConfig getFlowEngineConfig() {
        if (flowEngineConfig == null) {
            flowEngineConfig = BeanSwitchSpi.getBeanManage().getBean(FlowEngineConfig.class);
        }
        return flowEngineConfig;
    }

    public static void setFlowEngineConfig(FlowEngineConfig flowEngineConfig) {
        FlowEngineConfigHelper.flowEngineConfig = flowEngineConfig;
    }
}
