package com.imooc.sell.service.impl;

import com.imooc.sell.exception.SellException;
import com.imooc.sell.utils.KeyUtil;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SecKillService {

    private static final int TIMEOUT = 10 * 1000;
    @Autowired
    private RedisLock redisLock;

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

    public void orderProductMockDiffUser(String productId) {
        // 加锁
        long time = System.currentTimeMillis() + TIMEOUT;
        boolean lock = redisLock.lock(productId, String.valueOf(time));
        if (!lock) {
            // 没获取到锁
            throw new SellException(101, "哎哟喂，人也太多了，换个姿势再试试");
        }

        // 1. 查询该商品库存，为0 则活动结束
        int stockNum = stock.get(productId);
        if (stockNum == 0) {
            throw new SellException(100, "活动结束");
        } else {
            orders.put(KeyUtil.genUniqueKey(), productId);
            stockNum = stockNum - 1;
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            stock.put(productId, stockNum);
        }

        // 解锁
        redisLock.unlock(productId, String.valueOf(time));

    }
}
