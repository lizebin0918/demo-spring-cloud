package com.lzb.service.fallback;

import com.lzb.service.HelloService;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ProvideFallBack implements HelloService {

    @Override
    public String hello() {
        return "请求hello接口异常";
    }

    @Override
    public String exception() {
        return "请求exception异常";
    }

    @Override
    public String sleep() {
        System.out.println(LocalDateTime.now());
        return "请求sleep异常";
    }
}