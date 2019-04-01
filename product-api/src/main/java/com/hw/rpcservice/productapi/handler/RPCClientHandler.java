package com.hw.rpcservice.productapi.handler;

import com.hw.rpcservice.productapi.dto.RPCRequest;
import com.hw.rpcservice.productapi.dto.RPCResponse;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.CountDownLatch;

/**
 * @Description 客户端Handler
 * @Author hw
 * @Date 2019/3/28 11:51
 * @Version 1.0
 */
public class RPCClientHandler extends ChannelHandlerAdapter {
    private final RPCRequest request;
    private RPCResponse response;
    private final CountDownLatch latch;

    public RPCClientHandler(RPCRequest request, CountDownLatch latch) {
        this.request = request;
        this.latch = latch;
    }

    public RPCResponse getResponse() {
        return response;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.getCause().printStackTrace();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        response = (RPCResponse) msg;
        latch.countDown();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
