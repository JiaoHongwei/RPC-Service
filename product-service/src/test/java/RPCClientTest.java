import com.hw.rpcservice.productapi.domain.Product;
import com.hw.rpcservice.productapi.register.ServiceDiscovery;
import com.hw.rpcservice.productapi.register.ZooKeeperConstant;
import com.hw.rpcservice.productapi.service.IProductService;
import com.hw.rpcservice.productservice.proxy.RPCProxy;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/28 12:15
 * @Version 1.0
 */
public class RPCClientTest {

    public static void main(String[] args) {
        ServiceDiscovery discovery = new ServiceDiscovery(ZooKeeperConstant.ZK_CONNECT);
        RPCProxy proxy = new RPCProxy(discovery);
        IProductService productService = proxy.create(IProductService.class);
        Product product = productService.queryById(100);
        System.out.println(product);

    }
}
