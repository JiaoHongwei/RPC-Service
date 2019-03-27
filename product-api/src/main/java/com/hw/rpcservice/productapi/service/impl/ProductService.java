package com.hw.rpcservice.productapi.service.impl;

import com.hw.rpcservice.productapi.domain.Product;
import com.hw.rpcservice.productapi.service.IProductService;

import java.math.BigDecimal;
import java.util.Random;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 15:07
 * @Version 1.0
 */
public class ProductService implements IProductService {
    public Product queryById(long id) {
        Product product = new Product();
        product.setId(id);
        product.setName("商品" + id);
        product.setPrice(BigDecimal.valueOf(new Random().nextDouble()));
        return product;
    }
}
