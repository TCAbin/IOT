package cn.com.gree.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

@Controller
public class TestController {

    @RequestMapping("test")
    @ResponseBody
    public String test(Date date){
        System.out.println(date);
        return "success";
    }
}
