package com.hw.rpcservice.productservice;

import com.hw.rpcservice.productapi.domain.Product;
import com.hw.rpcservice.productapi.service.IProductService;
import com.hw.rpcservice.productapi.service.impl.ProductService;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.Socket;

/**
 * @Description 服务调用方
 * @Author hw
 * @Date 2019/3/27 15:13
 * @Version 1.0
 */
public class ProductServiceMain {
    public static void main(String[] args) {
        IProductService productService = (IProductService) rpc(IProductService.class);
        Product product = productService.queryById(10);
        System.out.println(product);
    }

    private static Object rpc(final Class clazz) {

        return Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new InvocationHandler() {
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                Socket socket = new Socket("127.0.0.1", 8888);
                String clazzName = clazz.getName();
                String methodName = method.getName();
                Class<?>[] parameterTypes = method.getParameterTypes();
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeUTF(clazzName);
                outputStream.writeUTF(methodName);
                outputStream.writeObject(parameterTypes);
                outputStream.writeObject(args);
                outputStream.flush();

                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                Object o = inputStream.readObject();
                inputStream.close();
                outputStream.close();
                socket.close();
                return o;
            }
        });
    }
}
