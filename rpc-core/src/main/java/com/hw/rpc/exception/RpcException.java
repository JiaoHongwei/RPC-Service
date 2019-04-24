package com.hw.rpc.exception;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/4/24 11:29
 * @Version 1.0
 */
public class RpcException extends RuntimeException {

    public RpcException(String msg) {
        super(msg);
    }

    public RpcException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public RpcException(Throwable cause) {
        super(cause);
    }

}
