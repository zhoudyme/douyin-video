package me.zhoudongyu.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steve
 * @date 2019/04/04
 */

@RestController
public class HelloWorldController {

    @RequestMapping("hello")
    public String Hello() {
        return "Hello SpringBoot";
    }
}
