package com.hw.rpcservice.productapi.register;

/**
 * @Description ZooKeeper 常量接口
 * @Author hw
 * @Date 2019/3/27 18:01
 * @Version 1.0
 */
public interface ZooKeeperConstant {

    int ZK_SESSION_TIMEOUT = 10000;
    String ZK_CONNECT = "127.0.0.1:2181";
    String ZK_REGISTRY_PATH = "/registry";
    String ZK_DATA_PATH = ZK_REGISTRY_PATH + "/data";
    String ZK_IP_SPLIT = ":";
}
