package com.smart.flow;

import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BizTaskNode;
import com.smart.flow.execute.Chain;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pw
 * @Date 2025/11/24 10:11
 * @Version 1.0
 **/
@Slf4j
public class FlowEngineBus {

    private static final Map<String, BizTaskNode> bizTaskNode = new HashMap<>();

    private static final Map<String, Chain> taskExecutableHashMap = new HashMap<>();



    public static void addBizTaskNodes(String nodeId, Object bizO) {
        BizTaskNode bizTaskNode = (BizTaskNode) bizO;
        if (FlowEngineBus.bizTaskNode.containsKey(nodeId)) {
            throw new FlowEngineException(String.format("Node with id %s already exists", nodeId));
        }
        FlowEngineBus.bizTaskNode.put(nodeId, bizTaskNode);

    }

    public static BizTaskNode getTaskNode(String nodeId) {
        return bizTaskNode.get(nodeId);
    }

    public static void addTaskExecutable(String chainId, Chain chain) {
        if (taskExecutableHashMap.containsKey(chainId)) {
            throw new FlowEngineException(String.format("Chain with chainId %s already exists", chainId));
        }
        taskExecutableHashMap.put(chainId, chain);
    }

    public static Chain getTaskExecutable(String chainId) {
        return taskExecutableHashMap.get(chainId);
    }

}
