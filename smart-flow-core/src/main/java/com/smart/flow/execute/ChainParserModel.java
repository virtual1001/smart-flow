package com.smart.flow.execute;

import com.smart.flow.FlowConstant;
import com.smart.flow.exception.FlowEngineException;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author pw
 * @Date 2025/12/25 15:12
 * @Version 1.0
 **/
public class ChainParserModel implements FlowConstant {

    private String chainId;
    private String name;
    private String description;
    private List<ChainModel> chainModelList;


    public static class ChainModel {
        private Map<String, BaseParserModel> BaseParserModel = new HashMap<>();

        public Map<String, BaseParserModel> getBaseParserModel() {
            return BaseParserModel;
        }


    }

    public String getChainId() {
        return chainId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public List<ChainModel> getChainModelList() {
        return chainModelList;
    }

    public void setChainId(String chainId) {
        this.chainId = chainId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setChainModelList(List<ChainModel> chainModelList) {
        this.chainModelList = chainModelList;
    }

    public void parser(Document document) {
        Element rootElement = document.getRootElement();
        this.chainId = rootElement.attributeValue(CHAIN_ID);
        this.name = rootElement.attributeValue(NODE_NAME);

        this.description = rootElement.attributeValue(DESCRIPTION);
        if (StringUtils.isEmpty(chainId)) {
            throw new FlowEngineException("No chainId found");
        }
        if (StringUtils.isEmpty(name)) {
            throw new FlowEngineException("No name found");
        }
        if (StringUtils.isEmpty(description)) {
            throw new FlowEngineException("No description found");
        }

    }
}
