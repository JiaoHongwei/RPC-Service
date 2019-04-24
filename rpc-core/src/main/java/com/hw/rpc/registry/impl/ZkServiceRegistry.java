package com.hw.rpc.registry.impl;

import com.hw.rpc.exception.RpcException;
import com.hw.rpc.registry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/4/2 17:41
 * @Version 1.0
 */
public class ZkServiceRegistry extends ServiceRegistry {

    private final static Logger logger = LoggerFactory.getLogger(ZkServiceRegistry.class);


    /**
     * param
     */
    public static final String ENV = "env";
    public static final String ZK_ADDRESS = "zkaddress";        // zk registry address, like "ip1:port,ip2:port,ip3:port"
    public static final String ZK_DIGEST = "zkdigest";          // zk registry digest

    // ------------------------------ zk conf ------------------------------

    // config
    private static final String zkBasePath = "/xxl-rpc";
    private String zkEnvPath;
    private XxlZkClient xxlZkClient = null;

    private Thread refreshThread;
    private volatile boolean refreshThreadStop = false;

    private volatile ConcurrentMap<String, TreeSet<String>> registryData = new ConcurrentHashMap<String, TreeSet<String>>();
    private volatile ConcurrentMap<String, TreeSet<String>> discoveryData = new ConcurrentHashMap<String, TreeSet<String>>();



    @Override
    public void start(Map<String, String> param) {
        String zkAddress = param.get("ZK_ADDRESS");
        String zkDigest = param.get("ZK_DIGEST");
        String env = param.get("ENV");

        // valid
        if (zkAddress == null || zkAddress.trim().length() == 0) {
            throw new RpcException("rpc zk_address can not be empty");
        }
        // init zk_path
        if (env == null || env.trim().length() == 0) {
            throw new RpcException("rpc env can not be empty");
        }




    }

    @Override
    public void stop() {

    }

    @Override
    public boolean registry(Set<String> keys, String value) {
        return false;
    }

    @Override
    public boolean remove(Set<String> keys, String value) {
        return false;
    }

    @Override
    public Map<String, TreeSet<String>> discovery(Set<String> keys) {
        return null;
    }

    @Override
    public TreeSet<String> discovery(String keys) {
        return null;
    }
}
