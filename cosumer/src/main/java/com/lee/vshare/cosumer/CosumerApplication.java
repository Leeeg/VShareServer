package com.lee.vshare.cosumer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource(value = { "classpath:consumers.xml" })
public class CosumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CosumerApplication.class, args);
    }

}
