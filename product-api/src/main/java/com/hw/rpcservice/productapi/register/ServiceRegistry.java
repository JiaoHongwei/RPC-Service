package com.hw.rpcservice.productapi.register;

import org.apache.zookeeper.*;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import static com.hw.rpcservice.productapi.register.ZooKeeperConstant.*;

/**
 * @Description 连接ZooKeeper注册中心，创建服务注册目录
 * @Author hw
 * @Date 2019/3/27 17:37
 * @Version 1.0
 */
public class ServiceRegistry {
    /**
     * 服务注册代码，这里有个注意的地方就是：CreateMode.EPHEMERAL_SEQUENTIAL，
     * 使用临时节点的方式注册，这样在服务关闭时就会自动消失。不会留下死服务。
     */

    private final CountDownLatch latch = new CountDownLatch(1);
    private ZooKeeper zooKeeper;

    public ServiceRegistry() {
    }

    /**
     * 注册
     *
     * @param data
     */
    public void register(String data) {
        if (data != null) {
            zooKeeper = connectServer();
            if (zooKeeper != null) {

                try {
                    if (null == zooKeeper.exists(ZK_REGISTRY_PATH, false)) {
                        System.out.println("创建Node " + ZK_REGISTRY_PATH);
                        String path = zooKeeper.create(ZK_REGISTRY_PATH, null, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                        System.out.println(String.format("create zookeeper node (%s => %s)", path, null));
                    }
                    createNode(ZK_DATA_PATH, data);
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    private  ZooKeeper connectServer() {
        ZooKeeper zooKeeper = null;
        try {
            zooKeeper = new ZooKeeper(ZK_CONNECT, ZK_SESSION_TIMEOUT, new Watcher() {
                public void process(WatchedEvent watchedEvent) {
                    // 判断是否已经连接zk
                    if (watchedEvent.getState() == Event.KeeperState.SyncConnected) {
                        latch.countDown();
                        System.out.println("连接 ZooKeeper 成功.");
                    }
                }
            });
            latch.await();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return zooKeeper;
    }

    private void createNode(String dir, String data) {
        byte[] bytes = data.getBytes();
        try {
//            创建一个临时匿名节点
            if (null == zooKeeper.exists(dir, false)) {
                String path = zooKeeper.create(dir, bytes, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                System.out.println(String.format("create zookeeper node (%s => %s)", path, data));
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
