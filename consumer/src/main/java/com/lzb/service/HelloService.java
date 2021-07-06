package com.lzb.service;

import com.lzb.service.fallback.ProvideFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * <br/>
 * Created on : 2021-06-25 16:11
 *
 * @author chenpi
 */
@FeignClient(value = "providor", fallback = ProvideFallBack.class)
public interface HelloService {

    @GetMapping("/hello")
    String hello();

    @GetMapping("/exception")
    String exception();

    @GetMapping("/sleep")
    String sleep();

}
