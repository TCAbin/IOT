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
            res.getSession().setAttribute("user",userName);
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 11:41
     * 登录设置
     */
    @RequestMapping(value = "loginOption",method = RequestMethod.POST)
    public Result loginOption(HttpServletRequest res,String password){
        String userName = String.valueOf(res.getSession().getAttribute("user"));
        int flag = loginService.checkOptionPassword(userName, password);
        if(flag == 0){
            return new Result(true,"success");
        } else if(flag == 1){
            return new Result(false,"somebody is setting");
        } else {
            return new Result(false,"failed");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/28 13:45
     * 退出登录设置
     */
    @RequestMapping(value = "logoutOption")
    public Result logoutOption(HttpServletRequest res){
        String userName = String.valueOf(res.getSession().getAttribute("user"));
        loginService.logoutOption(userName);
        return new Result(true,"success");
    }






}
