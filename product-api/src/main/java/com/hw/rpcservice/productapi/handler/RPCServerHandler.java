package com.hw.rpcservice.productapi.handler;

import com.hw.rpcservice.productapi.dto.RPCRequest;
import com.hw.rpcservice.productapi.dto.RPCResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 18:49
 * @Version 1.0
 */
public class RPCServerHandler extends ChannelHandlerAdapter {
    private final Map<String, Object> handlerMap;

    public RPCServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        RPCResponse response = new RPCResponse();
        RPCRequest request = (RPCRequest) msg;

        response.setRequestId(request.getRequestId());
        try {
            Object result = handler(request);
            response.setResult(result);
        } catch (Exception e) {
            response.setError(e);
        }
        ctx.writeAndFlush(response);
    }

    private Object handler(RPCRequest request) throws InvocationTargetException {
        String className = request.getClassName();
        Object serviceBean = handlerMap.get(className);
        Class<?> serviceBeanClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        FastClass fastClass = FastClass.create(serviceBeanClass);
        FastMethod method = fastClass.getMethod(methodName, parameterTypes);
        return method.invoke(fastClass.newInstance(), parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(String.format("server caught exception %s", cause.getMessage()));
        ctx.close();
    }
}
