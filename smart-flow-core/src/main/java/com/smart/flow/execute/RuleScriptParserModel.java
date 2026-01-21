package com.smart.flow.execute;


import com.smart.flow.FlowConstant;
import org.dom4j.Element;

/**
 * @Author pw
 * @Date 2025/12/25 15:55
 * @Version 1.0
 **/
public class RuleScriptParserModel extends BaseParserModel implements FlowConstant {
    private String ruleScript;
    private String nextId;
    private String language;
    private String filePath;




    public String getRuleScript() {
        return ruleScript;
    }

    public String getNextId() {
        return nextId;
    }

    public String getLanguage() {
        return language;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setRuleScript(String ruleScript) {
        this.ruleScript = ruleScript;
    }

    public void setNextId(String nextId) {
        this.nextId = nextId;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void parser1(Element element) {

    }
}
