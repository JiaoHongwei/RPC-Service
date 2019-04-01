## RPC-Service Senior版

- 使用protostuff序列化方式(protobuf的Java实现)
- 通信方式采用基于Netty的NIO实现
- 服务注册使用Zookeeper

## 环境

- zookeeper-3.3.6

## 启动

1. 配置zookeeper： [ZooKeeperConstant.java: Lines 3-16](product-api/src/main/java/com/hw/rpcservice/productapi/register/ZooKeeperConstant.java#L3-L16)
2. 启动服务端生产： [RPCServerTest.java: Lines 3-15](product-api/src/test/java/RPCServerTest.java#L3-L15)
3. 启动客户端消费： [RPCClientTest.java: Lines 7-23](product-service/src/test/java/RPCClientTest.java#L7-L23)

