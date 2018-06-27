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


    /**
     * @author 260172
     * @date 2018/6/27 8:46
     * 登录函数
     */
    @RequestMapping(value = "login",method = RequestMethod.POST)
    @ResponseBody
    public Result login(HttpServletRequest res,String userName, String password){
        boolean flag = loginService.checkUser(userName, password);
        if(flag){
            res.getSession().setAttribute("user","user");
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }

    /**
     * @author 260172
     * @date 2018/6/27 8:46
     * 更新密码
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    @ResponseBody
    public Result update(HttpServletRequest res,String userName,String password,String operatePassword){
        boolean flag = loginService.update(userName,password,operatePassword);
        if(flag){
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }


}
