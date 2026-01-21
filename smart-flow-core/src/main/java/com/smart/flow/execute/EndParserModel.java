package com.smart.flow.execute;

import org.dom4j.Element;

/**
 * @Author pw
 * @Date 2025/12/25 16:23
 * @Version 1.0
 **/
public class EndParserModel extends BaseParserModel{
    private String nextId;
    @Override
    public void parser1(Element element) {

    }

    public String getNextId() {
        return nextId;
    }
}
