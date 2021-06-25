package com.lzb.web;

import com.lzb.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

}
