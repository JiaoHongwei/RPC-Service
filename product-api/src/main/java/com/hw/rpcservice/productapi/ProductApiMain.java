package com.hw.rpcservice.productapi;

import com.hw.rpcservice.productapi.service.IProductService;
import com.hw.rpcservice.productapi.service.impl.ProductService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description 服务API提供方
 * @Author hw
 * @Date 2019/3/27 15:03
 * @Version 1.0
 */
public class ProductApiMain {

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(8888);
            Socket socket = null;
            System.out.println("ServerSocket start at 8888...");
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Received a new request. next...");
                ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                // 读取网络协议
                String className = inputStream.readUTF();
                String methodName = inputStream.readUTF();
                Class<?>[] parameterTypes = (Class<?>[]) inputStream.readObject();
                Object[] methodArgs = (Object[]) inputStream.readObject();

                // API 到具体实现的映射关系 简单的服务注册
                Class clazz = null;
                if (className.equals(IProductService.class.getName())) {
                    // 实现类
                    clazz = ProductService.class;
                }

                Method method = clazz.getMethod(methodName, parameterTypes);
                Object result = method.invoke(clazz.newInstance(), methodArgs);
                ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                outputStream.writeObject(result);
                outputStream.flush();
                System.out.println("Request processed. return " + result);

                inputStream.close();
                outputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
