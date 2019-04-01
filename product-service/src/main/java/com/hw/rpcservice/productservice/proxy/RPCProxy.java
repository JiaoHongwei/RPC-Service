package com.hw.rpcservice.productservice.proxy;

import com.hw.rpcservice.productapi.dto.RPCRequest;
import com.hw.rpcservice.productapi.dto.RPCResponse;
import com.hw.rpcservice.productapi.register.ServiceDiscovery;
import com.hw.rpcservice.productapi.register.ZooKeeperConstant;
import com.hw.rpcservice.productservice.RPCClient;
import org.omg.PortableInterceptor.HOLDING;

import java.awt.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 19:16
 * @Version 1.0
 */
public class RPCProxy {
    private String serverAddress;
    private ServiceDiscovery serviceDiscovery;

    public RPCProxy(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public RPCProxy(ServiceDiscovery serviceDiscovery) {
        this.serviceDiscovery = serviceDiscovery;
    }

    public <T> T create(Class<?> interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class<?>[]{interfaceClass}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RPCRequest request = new RPCRequest();
                request.setRequestId(UUID.randomUUID().toString());
                request.setClassName(method.getDeclaringClass().getName());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);
                if (serviceDiscovery != null) {
                    // 发现服务
                    serverAddress = serviceDiscovery.dicover();
                }
                String[] array = serverAddress.split(ZooKeeperConstant.ZK_IP_SPLIT);
                String host = array[0];
                int port = Integer.parseInt(array[1]);

                RPCClient client = new RPCClient(host, port);
                RPCResponse response = client.sent(request);

                if (response.getError() != null) {
                    throw response.getError();
                }else {
                    return response.getResult();
                }
            }
        });
    }
}
