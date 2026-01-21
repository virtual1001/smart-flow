package com.smart.flow.execute.spi;
import com.smart.flow.FlowEngineBus;
import com.smart.flow.FlowEngineConfigHelper;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.*;
import com.smart.flow.execute.thread.ParallelEnum;
import com.smart.flow.execute.thread.ParallelExecutor;
import com.smart.flow.execute.thread.ParallelStrategyExecutorBuild;
import com.smart.flow.property.FlowEngineConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**、
 * 并行网关节点执行器
 * @Author pw
 * @Date 2025/11/25 14:28
 * @Version 1.0
 **/
@Executable(tagName = "parallel-gateway")
public class ParallelGatewayNodeTaskExecutable implements TaskExecutable {

    //并行网关节点线程池
    private String parallelThreadPoolExecutorClass;

    //并行执行枚举类
    private ParallelEnum parallelEnum;
    //异步最大等待时间
    private Integer asyncMaxWaitTime;
    //异步最大等待时间单位
    private TimeUnit asyncMaxWaitTimeUnit;
    private String nextNodeId;
    private String chanId;
    private String name;
    private String nodeId;
    private String description;

    private List<CommonNodeModel> commonNodeModelList;

    @Override
    public String execute(Integer slotIndex) {
        parallelEnum = parallelEnum == null ? ParallelEnum.ALL : parallelEnum;
        ParallelExecutor parallelExecutor = ParallelStrategyExecutorBuild.builder().getParallelExecutor(parallelEnum);
        if (parallelExecutor == null) {
            throw new FlowEngineException("并行策略执行器不存在,请先初始化");
        }
        parallelExecutor.execute(this, slotIndex);
        return nextNodeId;
    }

    @Override
    public void initTaskNode(BaseParserModel baseParserModel, String chanId) throws InstantiationException, IllegalAccessException {
        ParallelGatewayParserModel parallelGatewayParserModel = (ParallelGatewayParserModel) baseParserModel;
        if (StringUtils.isBlank(parallelGatewayParserModel.getNodeId())){
            throw new FlowEngineException("并行网关节点ID不能为空");
        }
        Chain chain = FlowEngineBus.getTaskExecutable(chanId);

        List<CommonNodeModel> commonNodeModelList = parallelGatewayParserModel.getCommonNodeModelList();
        for (CommonNodeModel commonNodeModel : commonNodeModelList) {
            String tagName1 = commonNodeModel.getTagName();
            TaskExecutable taskExecutable1 = FlowDataBus.getOriginalTaskExecutableMap(tagName1).newInstance();
            taskExecutable1.initTaskNode(commonNodeModel, chanId);
            chain.addTaskExecutable(commonNodeModel.getNodeId(), taskExecutable1);
        }
        if (CollectionUtils.isEmpty(commonNodeModelList)) {
            throw new FlowEngineException("并行网关节点没有配置并行任务节点");
        }
        this.commonNodeModelList = commonNodeModelList;
        this.parallelThreadPoolExecutorClass = parallelGatewayParserModel.getParallelThreadPoolExecutorClass();
        FlowEngineConfig flowEngineConfig = FlowEngineConfigHelper.getFlowEngineConfig();
        this.asyncMaxWaitTime = flowEngineConfig.getAsyncMaxWaitTime();
        this.asyncMaxWaitTimeUnit = flowEngineConfig.getAsyncMaxWaitTimeUnit();
        this.parallelEnum = parallelGatewayParserModel.getParallelEnum();
        this.nextNodeId = parallelGatewayParserModel.getNextId();
        this.nodeId = parallelGatewayParserModel.getNodeId();
        this.name = parallelGatewayParserModel.getName();
        this.description = parallelGatewayParserModel.getDescription();
        this.chanId = chanId;


    }


    public String getChanId() {
        return chanId;
    }

    public String getParallelThreadPoolExecutorClass() {
        return parallelThreadPoolExecutorClass;
    }

    public ParallelEnum getParallelEnum() {
        return parallelEnum;
    }

    public Integer getAsyncMaxWaitTime() {
        return asyncMaxWaitTime;
    }

    public TimeUnit getAsyncMaxWaitTimeUnit() {
        return asyncMaxWaitTimeUnit;
    }

    public String getNextNodeId() {
        return nextNodeId;
    }

    public List<CommonNodeModel> getCommonNodeModelList() {
        return commonNodeModelList;
    }
}
