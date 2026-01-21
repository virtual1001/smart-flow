package com.smart.flow.execute;

import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Element;

import java.util.List;

/**
 * 排他网关节点模型
 * @Author pw
 * @Date 2025/12/25 15:27
 * @Version 1.0
 **/
public class ExclusiveGatewayParserModel extends BaseParserModel{
    private List<ExclusiveGatewayModel> exclusiveGatewayList;


    public static class ExclusiveGatewayModel {
        private String nodeId;
        private String ruleScript;

        public String getNodeId() {
            return nodeId;
        }

        public String getRuleScript() {
            return ruleScript;
        }

        public void setRuleScript(String ruleScript) {
            this.ruleScript = ruleScript;
        }

        public void setNodeId(String nodeId) {
            this.nodeId = nodeId;
        }
    }

    @Override
    public void parser1(Element element) {
        List<ExclusiveGatewayModel> exclusiveGatewayList = new java.util.ArrayList<>();
        List<Element> exclusiveGatewayElementList = element.elements();
        if (CollectionUtils.isNotEmpty(exclusiveGatewayElementList)) {
            for (Element exclusiveGatewayElement : exclusiveGatewayElementList) {
                ExclusiveGatewayModel exclusiveGatewayModel = new ExclusiveGatewayModel();
                exclusiveGatewayModel.setNodeId(exclusiveGatewayElement.attributeValue(GO_TO));
                exclusiveGatewayModel.setRuleScript(exclusiveGatewayElement.attributeValue(RULE_SCRIPT));
                exclusiveGatewayList.add(exclusiveGatewayModel);
            }
        }
        this.exclusiveGatewayList = exclusiveGatewayList;
    }

    public List<ExclusiveGatewayModel> getExclusiveGatewayList() {
        return exclusiveGatewayList;
    }
}
