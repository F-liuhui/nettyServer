package com.cnpc.contrllor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
@RequestMapping("/test")
public class TestContrllor {
    @RequestMapping(params = "method=test")
    public String test(){
       return "test";
    }
}
