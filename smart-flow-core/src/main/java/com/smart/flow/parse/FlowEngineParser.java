package com.smart.flow.parse;

import org.dom4j.DocumentException;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

/**
 * 流程引擎解析器
 */
public interface FlowEngineParser {
    void flowParser(List<String> flowData) throws DocumentException, IOException, URISyntaxException, InstantiationException, IllegalAccessException;
}
