package com.lee.vshare.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * @Create by lee
 * @emil JefferyLeeeg@gmail.com
 * @Date 19-4-16
 * @Time 下午4:31
 */
@Configuration
public class RestTemplateConfig {

    @Bean("httpClient")
    public RestTemplate httpClientRestTemplate() {
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setConnectTimeout(5000);
        requestFactory.setReadTimeout(5000);
        return new RestTemplate(requestFactory);
    }

}
