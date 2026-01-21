package com.smart.flow.execute.spi;


import com.smart.flow.execute.BaseParserModel;

/**
 * @Author pw
 * @Date 2025/11/24 19:02
 * @Version 1.0
 **/
public interface TaskExecutable {
    String execute(Integer slotIndex);
    void initTaskNode(BaseParserModel baseParserModel, String chainId) throws InstantiationException, IllegalAccessException;

}
