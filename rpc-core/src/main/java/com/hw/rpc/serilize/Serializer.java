package com.hw.rpc.serilize;

import com.hw.rpc.serilize.impl.ProtostuffSerializer;

/**
 * Tips：模板方法模式：定义一个操作中算法的骨架（或称为顶级逻辑），将一些步骤（或称为基本方法）的执行延迟到其子类中；
 * Tips：基本方法：抽象方法 + 具体方法final + 钩子方法；
 * Tips：Enum 时最好的单例方案；枚举单例会初始化全部实现，此处改为托管Class，避免无效的实例化；
 *
 * @Author hw
 * @Date 2019/4/1 15:24
 * @Version 1.0
 */
public abstract class Serializer {

    public abstract <T> byte[] serialize(T obj);

    public abstract <T> Object deserialize(byte[] bytes, Class<T> clazz);

    public enum SerializeEum {

        PROTOSTUFF(ProtostuffSerializer.class);

        private Class<? extends Serializer> serializerClass;

        SerializeEum(Class<? extends Serializer> serializerClass) {
            this.serializerClass = serializerClass;
        }

        public Serializer getSerializer() {
            try {
                return serializerClass.newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            return null;
        }

        public static SerializeEum match(String name, SerializeEum defaultSerializer) {
            for (SerializeEum serialize : SerializeEum.values()) {
                if (serialize.name().equals(name)) {
                    return serialize;
                }
            }
            return defaultSerializer;
        }
    }


}
