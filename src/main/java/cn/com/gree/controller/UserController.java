package cn.com.gree.controller;

import cn.com.gree.service.UserService;
import cn.com.gree.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("user")
public class UserController {

    @Resource(name = "UserService")
    private UserService userService;

    /**
     * @author 260172
     * @date 2018/6/27 8:46
     * 更新密码
     */
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public Result update(HttpServletRequest res, String userName, String password, String operatePassword){
        boolean flag = userService.update(userName,password,operatePassword);
        if(flag){
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }
}
