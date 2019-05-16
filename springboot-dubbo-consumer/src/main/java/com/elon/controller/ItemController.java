package com.elon.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.elon.item.api.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author 徐贵平
 * @time 2018年05月14日
 * @since JDK1.7
 **/
@Controller
public class ItemController {

    @Reference(version = "1.0.0")
    private ItemService itemService;

    @RequestMapping("/queryItemById")
    @ResponseBody
    public Object queryItemById(Integer itemId) {
        System.out.println("==================");
        return itemService.query(itemId);
    }

}
