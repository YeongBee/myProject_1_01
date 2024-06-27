package com.yeongbee.store.mydelight;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MainController {

//    @GetMapping("/")
    @ResponseBody
    public String def(){
        return  "<h1>Hello</h1>";
    }

    @GetMapping("/")
    public String myhome(){
        return "portfolio/home";
    }
}
