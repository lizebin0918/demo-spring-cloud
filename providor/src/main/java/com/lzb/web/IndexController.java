package com.lzb.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

/**
 * <br/>
 * Created on : 2021-04-13 21:01
 * @author lizebin
 */
@RestController
public class IndexController {

    @Value("${server.port}")
    private int port;

    @Autowired
    DiscoveryClient client;

    @RequestMapping("/hello")
    public String hello() {
        return "hello-" + port;
    }

    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }

    @RequestMapping("/test")
    public String client() {
        RestTemplate restTpl = getRestTemplate();
        return "client";
    }

}
