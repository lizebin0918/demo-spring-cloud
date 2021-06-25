package com.lzb.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <br/>
 * Created on : 2021-06-25 16:11
 *
 * @author chenpi
 */
@FeignClient("providor")
public interface HelloService {

    @GetMapping("/hello")
    String hello();

}
