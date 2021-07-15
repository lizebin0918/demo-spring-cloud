package com.lzb.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * <br/>
 * Created on : 2021-07-15 23:00
 *
 * @author lizebin
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @PostMapping("")
    public String read(@RequestParam String random, @RequestBody JSONObject body) {
        System.out.println("random = " + random);
        System.out.println("body = " + JSON.toJSONString(body));
        return "success";
    }

}
