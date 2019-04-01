package com.hw.rpcservice.productapi.dto;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 18:42
 * @Version 1.0
 */
public class RPCRequest {
    /**
     * 比较简单没什么好说的，唯一值得注意的就是请求和响应都带有Id，这是因为NIO通信是异步的，
     * 如果出现一个客户端发送了多个请求，那么也会有多个响应，
     * 由于是异步的，那么就免不了，出现不一致的对应情况，这时候可以用ID将每个请求和响应对应起来。
     */

    private String requestId;
    private String className;
    private String methodName;
    private Class<?>[] parameterTypes;
    private Object[] parameters;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
