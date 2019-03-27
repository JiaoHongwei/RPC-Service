package com.hw.rpcservice.productapi.service;

import com.hw.rpcservice.productapi.domain.Product;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 15:01
 * @Version 1.0
 */
public interface IProductService {
    Product queryById(long id);
}
