package com.smart.flow.exception;

/**
 * @Author pw
 * @Date 2025/11/22 14:37
 * @Version 1.0
 **/
public class FlowEngineException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public FlowEngineException(String message) {
        this.message = message;
    }

    public FlowEngineException(String message,Exception e) {
        super( message,e);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
