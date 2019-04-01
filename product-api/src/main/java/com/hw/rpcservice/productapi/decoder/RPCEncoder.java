package com.hw.rpcservice.productapi.decoder;

import com.hw.rpcservice.productapi.serialize.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Description Object,Object –> byte[]
 * @Author hw
 * @Date 2019/3/27 18:38
 * @Version 1.0
 */
public class RPCEncoder extends MessageToByteEncoder<Object> {
    private Class<?> genericClass;

    public RPCEncoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = ProtostuffSerializer.serialize(in);
            out.writeBytes(data);
        }
    }
}
