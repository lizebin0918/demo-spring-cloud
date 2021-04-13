package com.lzb.web;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <br/>
 * Created on : 2021-04-13 21:01
 * @author lizebin
 */
@RestController
public class IndexController {

    /**
     * 用于拉取服务映射，用于调用
     */
    @Autowired
    DiscoveryClient client;

    @RequestMapping("/hello")
    public String hello() {
        return "hello";
    }

    @RequestMapping("/client")
    public String client() {
        List<String> serviceIds = client.getServices();
        for (String serviceId : serviceIds) {
            List<ServiceInstance> services = client.getInstances(serviceId);
            System.out.println("----------------client--------------------");
            System.out.println(JSON.toJSONString(services));
            System.out.println("----------------client--------------------");
        }
        System.out.println(ToStringBuilder.reflectionToString(client.getServices()));
        return "client";
    }

}
