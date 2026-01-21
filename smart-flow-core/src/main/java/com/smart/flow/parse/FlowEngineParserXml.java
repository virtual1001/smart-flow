package com.smart.flow.parse;

import com.smart.flow.FlowConstant;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BaseParserModel;
import com.smart.flow.execute.ChainParserModel;
import com.smart.flow.execute.FlowEngineModelEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * xml解析器
 * @Author pw
 * @Date 2025/11/22 15:17
 * @Version 1.0
 **/
public class FlowEngineParserXml extends AbstractFlowEngine implements FlowConstant {
    @Override
    public void flowParser(List<String> flowPath) throws DocumentException, IOException, URISyntaxException, InstantiationException, IllegalAccessException {
        //解析路径
        List<String> contentList = parserContent(flowPath);
        if (CollectionUtils.isEmpty(contentList)) {
            throw new FlowEngineException("No content found");
        }
        List<Document> documentList = new ArrayList<>();
        for (String content : contentList) {
            org.dom4j.Document document = DocumentHelper.parseText(content);
            documentList.add(document);
        }
        List<ChainParserModel> chainParserModelList = buildChainParserModel(documentList);
        initTaskNode(chainParserModelList);


    }

    private List<ChainParserModel> buildChainParserModel(List<Document> documentList) throws InstantiationException, IllegalAccessException {

        List<ChainParserModel> chainParserModelList = new ArrayList<>();

        for (Document document : documentList) {
            ChainParserModel chainParserModel = new ChainParserModel();
            chainParserModel.parser(document);
            Element rootElement = document.getRootElement();
            List<Element> elements = rootElement.elements();
            List<ChainParserModel.ChainModel> chainModelList = new ArrayList<>();
            for (Element element : elements) {
                ChainParserModel.ChainModel chainModel = new ChainParserModel.ChainModel();
                String tagName = element.getName();
                FlowEngineModelEnum flowEngineModelEnum = FlowEngineModelEnum.getByTagName(tagName);
                BaseParserModel baseParserModel = flowEngineModelEnum.getClazz().newInstance();
                baseParserModel.parser(element);
                chainModel.getBaseParserModel().put(tagName, baseParserModel);
                chainModelList.add(chainModel);
            }

            chainParserModel.setChainModelList(chainModelList);
            chainParserModelList.add(chainParserModel);

        }
        return chainParserModelList;
    }
}
