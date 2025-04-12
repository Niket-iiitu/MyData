package com.MyData.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {
    @GetMapping(value = "/ping")
    private String pingCheck(){
        return "Server Active";
    }
}
