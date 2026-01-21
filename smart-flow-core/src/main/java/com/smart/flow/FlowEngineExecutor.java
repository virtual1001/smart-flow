package com.smart.flow;

import com.google.common.collect.Lists;

import com.smart.flow.annotation.BizNodeAnnotation;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BizTaskNode;
import com.smart.flow.execute.Chain;
import com.smart.flow.execute.FlowDataBus;
import com.smart.flow.parse.FlowEngineParser;
import com.smart.flow.property.FlowEngineConfig;
import com.smart.flow.spi.BeanSwitchSpi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reflections.Reflections;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ConfigurationBuilder;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Set;


/**
 * @Author pw
 * @Date 2025/9/29 16:49
 * @Version 1.0
 **/
@Slf4j
public class FlowEngineExecutor implements FlowConstant{

    private FlowEngineConfig flowEngineConfig;

    public void execute(String chainId,Context context) {
        //获取槽位
        Integer slotIndex = FlowDataBus.getSlotIndex(context);
        if (slotIndex == -9999) {
            throw new FlowEngineException("No slot found for " + chainId);
        }
        Context currentThreadContext = FlowDataBus.getCurrentThreadContext(slotIndex);
        if (currentThreadContext == null) {
            throw new FlowEngineException("No current thread context found for " + chainId);
        }


        try{
            Chain chain = FlowEngineBus.getTaskExecutable(chainId);
            if (chain == null) {
                throw new FlowEngineException("No chain found for " + chainId);
            }
            chain.execute(slotIndex);
        }catch (FlowEngineException e){
            logger.error("Error while executing chain {}", chainId, e);
           throw e;
        }finally {
            FlowDataBus.clear(slotIndex);
        }




    }

    public FlowEngineExecutor (){
        FlowDataBus.init();
    }

    public void setFlowEngineConfig(FlowEngineConfig flowEngineConfig) {
        this.flowEngineConfig = flowEngineConfig;
        FlowEngineConfigHelper.setFlowEngineConfig(flowEngineConfig);
    }




    public void init() {
        String scan = flowEngineConfig.getScan();
        if (Objects.isNull(scan)) {
            throw new  FlowEngineException("Flow engine scan is missing");
        }
        Reflections  reflections = new Reflections(new ConfigurationBuilder()
                .forPackages(scan)
                .addScanners(new TypeAnnotationsScanner()));
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(BizNodeAnnotation.class);
        for (Class<?> node : typesAnnotatedWith) {
            if (BizTaskNode.class.isAssignableFrom(node)) {
                BizNodeAnnotation annotation = node.getAnnotation(BizNodeAnnotation.class);
                String[] nodeIds = annotation.nodeId();
                if (nodeIds == null || nodeIds.length == 0) {
                    String nodeId = StringUtils.uncapitalize(node.getSimpleName());
                    Object taskNode = BeanSwitchSpi.getBeanManage().registerBean(nodeId, node);
                    FlowEngineBus.addBizTaskNodes(nodeId, taskNode);
                    continue;
                }
                for (String nodeId : nodeIds) {
                    Object taskNode = BeanSwitchSpi.getBeanManage().registerBean(nodeId, node);
                    FlowEngineBus.addBizTaskNodes(nodeId, taskNode);
                }
            }
        }

        //获取解析器
        String ruleSource = flowEngineConfig.getRuleSource();
        if (Objects.isNull(ruleSource)) {
            throw new FlowEngineException("Flow engine rule source is missing");
        }
        try{
            String replace = ruleSource.replace(" ", "");
            List<String> ruleSourceList = Arrays.asList(replace.split(","));
            for (String rule : ruleSourceList) {
                //根据路径获取对应的解析器
                FlowEngineParser flowEngineParser = FlowEngineParserFactory.lookup(rule);

                flowEngineParser.flowParser(Lists.newArrayList(rule));
                logger.warn("流程文件加载完成 {}", rule);

            }
        }catch (Exception e){
            logger.error(e.getMessage(),e);
            throw new  FlowEngineException(e.getMessage());
        }
    }

}
