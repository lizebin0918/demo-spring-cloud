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

    /**
     *
     [
     {
     "host": "192.168.31.253",
     "instanceId": "192.168.31.253:client-8081:8081",
     "instanceInfo": {
     "actionType": "ADDED",
     "appName": "CLIENT-8081",
     "coordinatingDiscoveryServer": false,
     "countryId": 1,
     "dataCenterInfo": {
     "name": "MyOwn"
     },
     "dirty": false,
     "healthCheckUrl": "http://192.168.31.253:8081/actuator/health",
     "healthCheckUrls": [
     "http://192.168.31.253:8081/actuator/health"
     ],
     "homePageUrl": "http://192.168.31.253:8081/",
     "hostName": "192.168.31.253",
     "iPAddr": "192.168.31.253",
     "id": "192.168.31.253:client-8081:8081",
     "instanceId": "192.168.31.253:client-8081:8081",
     "lastDirtyTimestamp": 1618408105608,
     "lastUpdatedTimestamp": 1618408105681,
     "leaseInfo": {
     "durationInSecs": 90,
     "evictionTimestamp": 0,
     "registrationTimestamp": 1618408105680,
     "renewalIntervalInSecs": 30,
     "renewalTimestamp": 1618408105680,
     "serviceUpTimestamp": 1618408105681
     },
     "metadata": {
     "management.port": "8081"
     },
     "overriddenStatus": "UNKNOWN",
     "port": 8081,
     "sID": "na",
     "securePort": 443,
     "secureVipAddress": "client-8081",
     "status": "UP",
     "statusPageUrl": "http://192.168.31.253:8081/actuator/info",
     "vIPAddress": "client-8081",
     "version": "unknown"
     },
     "metadata": {
     "$ref": "$[0].instanceInfo.metadata"
     },
     "port": 8081,
     "scheme": "http",
     "secure": false,
     "serviceId": "CLIENT-8081",
     "uri": "http://192.168.31.253:8081"
     }
     ]
     * @return
     */
    @RequestMapping("/client")
    public String client() {
        List<String> serviceIds = client.getServices();
        for (String serviceId : serviceIds) {
            List<ServiceInstance> services = client.getInstances(serviceId);
            //
            System.out.println("----------------client--------------------");
            System.out.println(JSON.toJSONString(services));
            System.out.println("----------------client--------------------");
        }
        System.out.println(ToStringBuilder.reflectionToString(client.getServices()));
        return "client";
    }

}
