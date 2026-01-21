package com.smart.flow;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author pw
 * @Date 2025/11/26 14:01
 * @Version 1.0
 **/
public abstract class Context {
    private Map<String, Object> params = new HashMap<>();

    public Map<String, Object> getParams(){
        return params;
    }
}
