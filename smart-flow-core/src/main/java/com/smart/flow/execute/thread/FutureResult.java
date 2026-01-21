package com.smart.flow.execute.thread;

/**
 * @Author pw
 * @Date 2025/12/18 16:14
 * @Version 1.0
 **/
public class FutureResult {
    private boolean success;
    private boolean timeout;
    private Exception exception;


    public static FutureResult success() {
        FutureResult result = new FutureResult();
        result.setSuccess(true);
        result.setTimeout(false);
        return result;
    }

    public static FutureResult fail(Exception exception) {
        FutureResult result = new FutureResult();
        result.setSuccess(false);
        result.setTimeout(false);
        result.setException(exception);
        return result;
    }

    public static FutureResult timeout(String nodeId) {
        FutureResult result = new FutureResult();
        result.setSuccess(false);
        result.setTimeout(true);
        result.setException(new Exception("nodeId:" + nodeId + " timeout"));
        return result;
    }


    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isTimeout() {
        return timeout;
    }


    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setTimeout(boolean timeout) {
        this.timeout = timeout;
    }
}
