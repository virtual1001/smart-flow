package com.smart.flow.execute;

/**
 * @Author pw
 * @Date 2025/12/25 16:53
 * @Version 1.0
 **/
public enum FlowEngineModelEnum {
    COMMON_NODE("common-node","普通节点",CommonNodeModel.class)

    ,EXCLUSIVE_GATEWAY("exclusive-gateway","排他网关",ExclusiveGatewayParserModel.class)

    ,PARALLEL_GATEWAY("parallel-gateway","并行网关",ParallelGatewayParserModel.class)

    ,RULE_SCRIPT("rule-script","脚本节点",RuleScriptParserModel.class)

    ,START("start","开始节点",StartParserModel.class)

    ,END("end","结束节点",EndParserModel.class);


    public static FlowEngineModelEnum getByTagName(String tagName) {
        for (FlowEngineModelEnum value : values()) {
            if (value.tagName.equals(tagName)) {
                return value;
            }
        }
        return null;
    }

    private String tagName;
    private String description;
    private Class<? extends BaseParserModel> clazz;

    FlowEngineModelEnum(String tagName, String description, Class<? extends BaseParserModel> clazz) {
        this.tagName = tagName;
        this.description = description;
        this.clazz = clazz;
    }
    public String getTagName() {
        return tagName;
    }
    public String getDescription() {
        return description;
    }
    public Class<? extends BaseParserModel> getClazz() {
        return clazz;
    }
}
