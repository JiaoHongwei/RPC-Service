package com.hw.rpc.registry;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @Description 注册中心 模版
 * @Author hw
 * @Date 2019/4/1 15:21
 * @Version 1.0
 */
public abstract class ServiceRegistry {

    public abstract void start(Map<String, String> param);

    public abstract void stop();

    public abstract boolean registry(Set<String> keys, String value);

    public abstract boolean remove(Set<String> keys, String value);

    public abstract Map<String, TreeSet<String>> discovery(Set<String> keys);

    public abstract TreeSet<String> discovery(String keys);


}
