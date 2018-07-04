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
    public Result login(HttpServletRequest res,String username, String password){
        boolean flag = loginService.checkUser(username, password);
        if(flag){
            res.getSession().setAttribute("user",username);
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
        String username = String.valueOf(res.getSession().getAttribute("user"));
        int flag = loginService.checkOptionPassword(username, password);
        if(flag == 0){
            return new Result(true,"success");
        } else if(flag == 1){
            return new Result(false,"somebodySetting");
        } else {
            return new Result(false,"failed");
        }
    }

    /**
     * @author Abin
     * @date 2018/6/29 10:49
     * 退出登录
     */
    @RequestMapping(value = "logout")
    public Result logout(HttpServletRequest res){
        String username = String.valueOf(res.getSession().getAttribute("user"));
        loginService.logoutOption(username);
        res.getSession().invalidate();
        return new Result(true,"success");
    }

    /**
     * @author 260172
     * @date 2018/6/28 13:45
     * 退出登录设置
     */
    @RequestMapping(value = "logoutOption")
    public Result logoutOption(HttpServletRequest res){
        String username = String.valueOf(res.getSession().getAttribute("user"));
        loginService.logoutOption(username);
        return new Result(true,"success");
    }






}
