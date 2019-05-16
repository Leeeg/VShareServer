package com.elon.item.api.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.elon.item.api.ItemService;
import com.elon.item.entity.Item;

/**
 * @author 徐贵平
 * @time 2018年05月10日
 * @since JDK1.7
 **/
//dubbo必须制定对应的接口,否则dubbo代理就是void.class
@Service(version = "1.0.0")
public class ItemServiceImpl implements ItemService {

    @Override
    public void add(Item item) {

    }

    @Override
    public void delete(Integer integer) {

    }

    @Override
    public void update(Item item) {

    }

    @Override
    public Item query(Integer k) {
        System.out.println("zookeeper test success !");
        return null;
    }
}
