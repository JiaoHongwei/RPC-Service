package com.hw.rpcservice.productapi.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @Description TODO
 * @Author hw
 * @Date 2019/3/27 15:00
 * @Version 1.0
 */
public class Product implements Serializable {
    private long id;
    private String name;
    private BigDecimal price;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
