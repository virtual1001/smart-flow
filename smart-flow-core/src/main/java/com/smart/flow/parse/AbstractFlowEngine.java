package com.smart.flow.parse;


import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import com.smart.flow.FlowConstant;
import com.smart.flow.FlowEngineBus;
import com.smart.flow.annotation.Executable;
import com.smart.flow.exception.FlowEngineException;
import com.smart.flow.execute.BaseParserModel;
import com.smart.flow.execute.Chain;
import com.smart.flow.execute.ChainParserModel;
import com.smart.flow.execute.FlowDataBus;
import com.smart.flow.execute.spi.TaskExecutable;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.XML;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.*;

/**
 * @Author pw
 * @Date 2025/11/22 15:35
 * @Version 1.0
 **/
@Slf4j
public abstract class AbstractFlowEngine implements FlowEngineParser, FlowConstant {


    public void initTaskNode(List<ChainParserModel> chainParserModelList) throws InstantiationException, IllegalAccessException {
        ServiceLoader.load(TaskExecutable.class).forEach(taskExecutable -> {
            Executable executable = taskExecutable.getClass().getAnnotation(Executable.class);
            FlowDataBus.addOriginalTaskExecutableMap(executable.tagName(),taskExecutable.getClass());
        });
        for (ChainParserModel chainParserModel : chainParserModelList) {
            Chain chain = new Chain();
            chain.setChainId(chainParserModel.getChainId());
            chain.setDescription(chainParserModel.getDescription());
            chain.setName(chainParserModel.getName());

            FlowEngineBus.addTaskExecutable(chain.getChainId(), chain);
            List<ChainParserModel.ChainModel> chainModelList = chainParserModel.getChainModelList();
            for (ChainParserModel.ChainModel chainModel : chainModelList){
                Map<String, BaseParserModel> baseParserModelMap = chainModel.getBaseParserModel();

                baseParserModelMap.forEach((tagName, baseParserModel) -> {
                    TaskExecutable taskExecutable = null;
                    try {
                        taskExecutable = FlowDataBus.getOriginalTaskExecutableMap(tagName).newInstance();
                        taskExecutable.initTaskNode(baseParserModel,chainParserModel.getChainId());
                    }catch (Exception e) {
                        logger.error("初始化任务执行器失败：{}", baseParserModel.getNodeId(),e);
                        throw new FlowEngineException("初始化任务执行器失败：" + baseParserModel.getNodeId());
                    }
                    chain.addTaskExecutable(baseParserModel.getNodeId(), taskExecutable);
                });

            }
        }
    }

    public List<String> parserContent(List<String> rulePath) throws IOException, URISyntaxException {
        if (CollectionUtils.isEmpty(rulePath)) {
            throw new FlowEngineException("rule path is empty");
        }
        List<String> content = new ArrayList<>();
        for (String rule : rulePath) {
            logger.warn("开始加载流程文件 {}", rule);
            if (Paths.get(rule).isAbsolute()) {
                throw new IllegalArgumentException("rule path is absolute");
            }


            try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(rule)) {
                if (inputStream == null) {
                    throw new FlowEngineException("流程文件不存在：" + rule);
                }
                // Java 8 方式将 InputStream 转为字符串（兼容 Jar 包内资源）
                String xmlContent = inputStreamToString(inputStream, StandardCharsets.UTF_8);
                content.add(xmlContent);
            } catch (IOException e) {
                logger.error("读取流程文件失败：{}", rule, e);
                throw new FlowEngineException("读取流程文件失败：" + rule);
            }
        }
        return content;
    }

    private String inputStreamToString(InputStream inputStream, Charset charset) throws IOException {
        // Java 8 Scanner 方式（兼容所有场景，无额外依赖）
        try (Scanner scanner = new Scanner(inputStream, charset.name())) {
            scanner.useDelimiter("\\A"); // 读取整个流
            return scanner.hasNext() ? scanner.next() : "";
        }
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InstantiationException, IllegalAccessException {
//        URL resource = AbstractFlowEngine.class.getClassLoader().getResource("xxx.txt");
//
//        Path path = Paths.get(resource.toURI());
//        byte[] bytes = Files.readAllBytes(path);
//        System.out.println(new String(bytes, StandardCharsets.UTF_8));
//
//
//        Map<String, TmpNode> tmpNodeMap = new HashMap<>();
//        TmpNode tmpNode = new TmpNode();
//        tmpNode.setNodeId("1");
//        tmpNode.setName("1");
//        tmpNode.setTagName("1");
//        tmpNodeMap.put("1", tmpNode);
//        TmpNode tmpNode1 = tmpNodeMap.get("1");
//        tmpNode1 = tmpNode1.getClass().newInstance();
//        System.out.println(tmpNode1);

        String xmlStr = "<user id = 'dafdsf' name= '张三' age = '20'><address city=\"北京\">朝阳区</address><address city=\"北京\">朝阳区</address><test city=\"北京\">朝阳区</test></user>";
        JsonNode node = new XmlMapper().readTree(xmlStr);
        Map<String, Object> map = new ObjectMapper().convertValue(node, HashMap.class);
        System.out.println(JSON.toJSONString( map));


        JSONObject jsonObject = XML.toJSONObject(xmlStr);
        JSONObject jsonObject1 = jsonObject.getJSONObject("user");
        JSONArray address = jsonObject1.getJSONArray("address");
        System.out.println(jsonObject);

    }


}
