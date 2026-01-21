package com.smart.flow;



import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.parse.FlowEngineParser;
import com.smart.flow.parse.FlowEngineParserJson;
import com.smart.flow.parse.FlowEngineParserXml;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

/**
 * @Author pw
 * @Date 2025/11/22 14:51
 * @Version 1.0
 **/
public class FlowEngineParserFactory {

    public static final String END_WIDTH_XML = ".xml";
    public static final String END_WIDTH_JSON = ".json";
    public static final String END_WIDTH_YML = ".yml";
    public static final String END_WIDTH_YAML = ".yaml";

    private static final Map<Predicate<String>, FlowEngineParser> parsers = new HashMap<Predicate<String>, FlowEngineParser>(){
        {
            put(path -> path.endsWith(END_WIDTH_JSON), new FlowEngineParserJson());
            put(path -> path.endsWith(END_WIDTH_XML), new FlowEngineParserXml());
            put(path -> path.endsWith(END_WIDTH_YML), new FlowEngineParserXml());
            put(path -> path.endsWith(END_WIDTH_YAML), new FlowEngineParserXml());
        }
    };

    public static FlowEngineParser lookup(String rulePath) {
        if (!isRuleFilePathName(rulePath)) {
            throw new FlowEngineException("Invalid rule path: " + rulePath);
        }
        Predicate<String> predicateKey = parsers.keySet()
                .stream()
                .filter(key -> key.test(rulePath))
                .findFirst()
                .orElseThrow(() -> new FlowEngineException("Invalid parser: " + rulePath));
        FlowEngineParser flowEngineParser = parsers.get(predicateKey);
        return flowEngineParser;

    }

    private static boolean isRuleFilePathName(String path) {
        return path.endsWith(END_WIDTH_XML)
                || path.endsWith(END_WIDTH_JSON)
                || path.endsWith(END_WIDTH_YAML)
                || path.endsWith(END_WIDTH_YML);
    }

}
