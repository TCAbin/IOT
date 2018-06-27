package cn.com.gree.controller;

import cn.com.gree.service.LoginService;
import cn.com.gree.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
public class LoginController {

    @Resource(name = "LoginService")
    private LoginService loginService;


    /**
     * @author 260172
     * @date 2018/6/27 8:46
     * 登录函数
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    public Result login(HttpServletRequest res,String userName, String password){
        boolean flag = loginService.checkUser(userName, password);
        if(flag){
            res.getSession().setAttribute("user","user");
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }






}
