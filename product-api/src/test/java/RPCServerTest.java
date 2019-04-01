import com.hw.rpcservice.productapi.RPCServer;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/28 12:14
 * @Version 1.0
 */
public class RPCServerTest {

    public static void main(String[] args) {
        int port = 9090;
        new RPCServer().initService(port);
    }
}
