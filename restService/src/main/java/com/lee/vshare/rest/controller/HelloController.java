package com.lee.vshare.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @title:
 * @gmail jefferyleeeg@gmail.com
 * @author:Lee
 * @date: 2018/11/9
 */
@RestController
@RequestMapping("/api/main")
public class HelloController extends BaseController {

    Logger logger = LoggerFactory.getLogger(HelloController.class);

    @GetMapping("/hello")
    @ApiOperation("hello word")
    public String hello(){
        logger.info("Hello Word !");
        return "Hello Springboot!";
    }

}
