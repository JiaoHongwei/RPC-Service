## RPC-Service master 最简单的手写RPC实现

- 使用Java原生序列化方式
- 通信方式采用的Socket是基于最原始的BIO实现
- 未实现服务注册

## RPC-Service senior 版请点击senior分支

- 使用protostuff序列化方式(protobuf的Java实现)
- 通信方式采用基于Netty的NIO实现
- 服务注册使用Zookeeper
- https://github.com/JiaoHongwei/RPC-Service/tree/senior
