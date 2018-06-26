package cn.com.gree.controller;

import cn.com.gree.service.LoginService;
import cn.com.gree.utils.Result;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

    @Resource(name = "LoginService")
    private LoginService loginService;


    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest res,String userName, String password){
        boolean flag = loginService.checkUser(userName, password);
        if(flag){
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }
    public Result update(HttpServletRequest res,String userName,String password,String operatePassword){
        boolean flag = loginService.update(userName,password,operatePassword);
        if(flag){
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }


}
