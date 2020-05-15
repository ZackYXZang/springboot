package com.example.springboot.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * test
 */
@RestController
@RequestMapping("test")
public class SpringBootTestController {

    @Value("${test.name}")
    private String name;

    @Value("${test.remark}")
    private String remark;

    @RequestMapping("remark")
    public String returnRemark(){
        return name +"'s " + remark + "!";
    }
}
