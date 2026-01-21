package com.smart.flow.execute;

import com.smart.flow.FlowConstant;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.spi.StartNodeTaskExecutable;
import com.smart.flow.execute.spi.TaskExecutable;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pw
 * @Date 2025/11/25 14:53
 * @Version 1.0
 **/
public class Chain {

    private String chainId ;
    private String name;
    private String description;

    private Map<String, TaskExecutable> taskExecutableMap = new HashMap<>();



    public void addTaskExecutable(String nodeId, TaskExecutable taskExecutable) {
        if (this.taskExecutableMap.containsKey(nodeId)) {
            throw new FlowEngineException("nodeId:"+nodeId+" already exists");
        }
        this.taskExecutableMap.put(nodeId, taskExecutable);
    }


    public void execute(Integer slotIndex) {
        StartNodeTaskExecutable startTaskExecutable = (StartNodeTaskExecutable)taskExecutableMap.get(FlowConstant.START);


        String nextId = startTaskExecutable.execute(slotIndex);

        while (StringUtils.isNotEmpty(nextId)) {
            TaskExecutable taskExecutable = taskExecutableMap.get(nextId);
            nextId = taskExecutable.execute(slotIndex);
        }
    }



    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTaskExecutableMap(Map<String, TaskExecutable> taskExecutableMap) {
        this.taskExecutableMap = taskExecutableMap;
    }

    public String getChainId() {
        return chainId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, TaskExecutable> getTaskExecutableMap() {
        return taskExecutableMap;
    }

}
