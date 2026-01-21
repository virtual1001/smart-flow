package com.smart.flow.execute;

import com.smart.flow.execute.thread.ParallelEnum;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 并行网关节点模型
 * @Author pw
 * @Date 2025/12/25 15:24
 * @Version 1.0
 **/

public class ParallelGatewayParserModel extends BaseParserModel{

    //并行执行枚举类
    private ParallelEnum parallelEnum;
    //异步最大等待时间
    private Integer asyncMaxWaitTime;
    //异步最大等待时间单位
    private TimeUnit asyncMaxWaitTimeUnit;
    //并行网关级别线程池
    private String parallelThreadPoolExecutorClass ;
    private List<CommonNodeModel> commonNodeModelList;
    private String nextId;
    private String parallelType;

    public String getParallelType() {
        return parallelType;
    }

    public String getNextId() {
        return nextId;
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

    public String getParallelThreadPoolExecutorClass() {
        return parallelThreadPoolExecutorClass;
    }

    public List<CommonNodeModel> getCommonNodeModelList() {
        return commonNodeModelList;
    }

    @Override
    public void parser1(Element element) {

        this.parallelThreadPoolExecutorClass = element.attributeValue(PARALLEL_THREAD_POOL_EXECUTOR_CLASS);
        this.parallelType = element.attributeValue(PARALLEL_TYPE);

        List<CommonNodeModel> commonNodeModelList = new ArrayList<>();
        List<Element> parallelGatewayElemntList = element.elements();
        for (Element parallelGatewayElement : parallelGatewayElemntList) {
            String tag = parallelGatewayElement.getName();
            if (COMMON_NODE.equals( tag)) {
                CommonNodeModel commonNodeModel = new CommonNodeModel();
                commonNodeModel.parser(parallelGatewayElement);
                commonNodeModelList.add(commonNodeModel);
            }
        }
        this.commonNodeModelList = commonNodeModelList;
        Element nextNodeElement = element.element(NEXT_NODE);
        if (nextNodeElement != null) {
            this.nextId = nextNodeElement.attributeValue(GO_TO);
        }
    }
}
