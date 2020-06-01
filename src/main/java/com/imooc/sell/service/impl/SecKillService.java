package com.imooc.sell.service.impl;

import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.KeyUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class SecKillService {

    static Map<String, Integer> sProducts;
    static Map<String, Integer> stock;
    static Map<String, String> orders;

    static {
        sProducts = new HashMap<>();
        stock = new HashMap<>();
        orders = new HashMap<>();
        sProducts.put("123456", 100000);
        stock.put("123456", 100000);
    }

    private String queryMap(String productId) {
        return "国庆活动，皮蛋瘦肉粥特价，限量份"
            + sProducts.get(productId)
            + " 还剩: " + stock.get(productId) + " 份"
            + " 该商品成功下单用户数：" + orders.size() + " 人";
    }

    public String querySecKillProductInfo(String productId) {
        return queryMap(productId);
    }

    /**
     * 加上synchronized, 保证多线程运行不会有问题，也可以是查询数据库，mysql是行级锁
     */
    public synchronized void orderProductMockDiffUser(String productId) {
        // 1. 查询该商品库存，为0 则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束");
        } else {
            orders.put(KeyUtil.genUniqueKey(), productId);
            stockNum = stockNum -1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

    }
}
