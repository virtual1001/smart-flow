package com.smart.flow.execute;

import org.dom4j.Element;

/**
 * @Author pw
 * @Date 2025/12/25 15:38
 * @Version 1.0
 **/
public class StartParserModel extends BaseParserModel{
    private String nextId;

    public String getNextId() {
        return nextId;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    @Override
    public void parser1(Element element) {
        Element nextNodeElement = element.element(NEXT_NODE);
        if (nextNodeElement != null) {
            this.nextId = nextNodeElement.attributeValue(GO_TO);
        }
    }
}
