package com.smart.flow.execute;

import com.smart.flow.FlowConstant;
import com.smart.flow.exception.FlowEngineException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Element;

/**
 * @Author pw
 * @Date 2025/12/25 15:13
 * @Version 1.0
 **/
public abstract class BaseParserModel implements ParserModel, FlowConstant {

    @Override
    public void parser(Element element) {
        this.tagName = element.getName();
        this.description = element.attributeValue(DESCRIPTION);
        this.name = element.attributeValue(NODE_NAME);
        this.nodeId = element.attributeValue(NODE_ID);
        if (StringUtils.isEmpty(nodeId)) {
            throw new FlowEngineException(this.tagName+" 节点 请检查配置nodeId为空");
        }
        if (StringUtils.isEmpty(name)) {
            throw new FlowEngineException(this.tagName+" 节点 请检查配置nodeName为空");
        }
        if (StringUtils.isEmpty(description)) {
            throw new FlowEngineException(this.tagName+" 节点 请检查配置description为空");
        }

        parser1(element);
    }

    public abstract void parser1(Element element);

    private String tagName;
    //节点名字
    private String name;
    //节点ID
    private String nodeId;
    //描述
    private String description;

    public String getTagName() {
        return tagName;
    }

    public String getName() {
        return name;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getDescription() {
        return description;
    }
}
