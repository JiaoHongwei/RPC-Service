package com.hw.rpcservice.productapi.decoder;

import com.hw.rpcservice.productapi.serialize.ProtostuffSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Description byte[] –> Object,Object –> byte[]
 * @Author hw
 * @Date 2019/3/27 18:18
 * @Version 1.0
 */
public class RPCDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;

    public RPCDecoder(Class<?> genericClass) {
        this.genericClass = genericClass;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        final int length = in.readableBytes();
        final byte[] bytes = new byte[length];
        in.readBytes(bytes, 0, length);
        Object obj = ProtostuffSerializer.deserialize(bytes, genericClass);
        out.add(obj);
    }
}
