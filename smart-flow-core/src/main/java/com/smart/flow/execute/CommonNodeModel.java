package com.smart.flow.execute;

import org.dom4j.Element;

/**
 * 普通任务节点模型
 * @Author pw
 * @Date 2025/12/25 15:00
 * @Version 1.0
 **/
public class CommonNodeModel extends BaseParserModel {

    private String nextId;

    public String getNextId() {
        return nextId;
    }

    @Override
    public void parser1(Element element) {
        Element nextNodeElement = element.element(NEXT_NODE);
        if (nextNodeElement != null) {
            nextId = nextNodeElement.attributeValue(GO_TO);
        }
    }
}
