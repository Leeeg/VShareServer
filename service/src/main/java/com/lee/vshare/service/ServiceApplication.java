package com.lee.vshare.service;

import com.lee.vshare.service.netty.NettyServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication(scanBasePackages={"com.lee.vshare.service","com.lee.vshare.common"})
@tk.mybatis.spring.annotation.MapperScan(basePackages = "com.lee.vshare.service.dao")
public class ServiceApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(ServiceApplication.class, args);
        //启动Netty服务
        NettyServer nettyServer = context.getBean(NettyServer.class);
        nettyServer.run();
    }

}
