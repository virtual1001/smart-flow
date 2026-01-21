package com.smart.flow;

/**
 * @Author pw
 * @Date 2025/11/22 14:37
 * @Version 1.0
 **/
public class TestException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public TestException(String message) {
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
