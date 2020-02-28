package com.jxwifi.kyc.dev.controller;

import com.jxwifi.kyc.dev.service.TranServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevTestController {
    @Autowired
    TranServiceImpl tranService;
    @GetMapping("/hi")
    public String hi(){
        tranService.sendMessageByQueue("test_hua","test11111");
        return "ok";
    }
}
