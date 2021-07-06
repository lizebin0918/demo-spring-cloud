package com.lzb.web;

import com.lzb.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

/**
 * <br/>
 * Created on : 2021-06-25 16:10
 *
 * @author chenpi
 */
@RestController
@RequestMapping
public class HelloController {

    @Autowired
    private HelloService helloService;

    @GetMapping("")
    public String hello() {
        return helloService.hello();
    }

    @GetMapping("/sleep")
    public String sleep() {
        System.out.println(LocalDateTime.now());
        return helloService.sleep();
    }

    @GetMapping("/exception")
    public String exception() {
        return helloService.exception();
    }

    /**
     * ribbon 完成客户端的负载均衡
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @GetMapping("/lb")
    public void lb() {
        ServiceInstance instance = loadBalancerClient.choose("providor");
        String url = "http://" + instance.getHost() + ":" + instance.getPort() + "/hello";
        RestTemplate rt = new RestTemplate();
        System.out.println("url:" + url);
        String response = rt.getForObject(url, String.class);
        System.out.println("response:" + response);
    }

}
