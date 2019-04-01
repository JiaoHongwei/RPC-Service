package com.hw.rpcservice.productapi;

import com.hw.rpcservice.productapi.decoder.RPCDecoder;
import com.hw.rpcservice.productapi.decoder.RPCEncoder;
import com.hw.rpcservice.productapi.dto.RPCRequest;
import com.hw.rpcservice.productapi.dto.RPCResponse;
import com.hw.rpcservice.productapi.handler.RPCServerHandler;
import com.hw.rpcservice.productapi.register.ServiceRegistry;
import com.hw.rpcservice.productapi.register.ZooKeeperConstant;
import com.hw.rpcservice.productapi.service.IProductService;
import com.hw.rpcservice.productapi.service.impl.ProductService;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description 服务API提供方
 * @Author hw
 * @Date 2019/3/27 15:03
 * @Version 1.0
 */
public class RPCServer {

    /**
     * 先将服务确定好，才能区调用，不允许客户端自动添加服务
     *
     * @return
     */
    private Map<String, Object> getServices() {
        HashMap<String, Object> services = new HashMap<String, Object>(1);
        services.put(IProductService.class.getName(), new ProductService());
        return services;
    }

    private void bind(int port) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        final Map<String, Object> services = getServices();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).option(ChannelOption.SO_BACKLOG, 100)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline()
                                    .addLast(new LengthFieldBasedFrameDecoder(65535, 0, 2, 0, 2))
                                    .addLast(new RPCDecoder(RPCRequest.class))
                                    .addLast(new LengthFieldPrepender(2))
                                    .addLast(new RPCEncoder(RPCResponse.class))
                                    .addLast(new RPCServerHandler(services));
                        }
                    });
            ChannelFuture channelFuture = serverBootstrap.bind(port).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static String getAddress() {
        InetAddress host = null;
        try {
            host = InetAddress.getLocalHost();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String address = host.getHostAddress();
        return address;
    }

    public void initService(int port) {
        ServiceRegistry registry = new ServiceRegistry();
        String address= getAddress();
        registry.register(address + ZooKeeperConstant.ZK_IP_SPLIT + port);
        bind(port);
    }


    public static void main(String[] args) {

        int port = 9090;
        new RPCServer().initService(port);
    }
}
