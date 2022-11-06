package com.ayoub.productservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public void HelloWorld(){
         System.out.println("Hello Ayoub!");
    }
}
