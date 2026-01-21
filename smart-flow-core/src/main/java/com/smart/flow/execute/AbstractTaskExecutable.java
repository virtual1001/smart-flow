package com.smart.flow.execute;

import com.alibaba.ttl.TransmittableThreadLocal;
import com.smart.flow.FlowEngineBus;
import com.smart.flow.execute.spi.TaskExecutable;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author pw
 * @Date 2025/11/26 10:35
 * @Version 1.0
 **/
@Slf4j
public abstract class AbstractTaskExecutable implements TaskExecutable {

    private TransmittableThreadLocal<Integer> THREAD_LOCAL = new TransmittableThreadLocal<>();
    //节点名字
    private String name;
    //节点ID
    private String nodeId;
    //描述
    private String description;

    @Override
    public String execute(Integer index) {
        BizTaskNode taskNode = FlowEngineBus.getTaskNode(nodeId);
        try {
            this.THREAD_LOCAL.set(index);
            taskNode.setTaskExecutable(this);
            return executeBiz(index);
        }catch (Exception e){
            throw e;
        }finally {
            taskNode.removeTaskExecutable();
            logger.info("clear threadLocal slot:{}", THREAD_LOCAL.get());
            this.THREAD_LOCAL.remove();


        }
    }

    @Override
    public void initTaskNode(BaseParserModel baseParserModel,String chanId) {
        CommonNodeModel commonNodeModel = (CommonNodeModel) baseParserModel;
        this.name = commonNodeModel.getName();
        this.nodeId = commonNodeModel.getNodeId();
        this.description = commonNodeModel.getDescription();

        initBizTaskNode(commonNodeModel);
    }


    public Integer getIndex() {
        return THREAD_LOCAL.get();
    }

    public abstract void initBizTaskNode(CommonNodeModel commonNodeModel);
    public abstract String executeBiz(Integer string) ;
}
