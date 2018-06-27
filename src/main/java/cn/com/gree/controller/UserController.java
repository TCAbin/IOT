package cn.com.gree.controller;

import cn.com.gree.aop.annotation.DataBaseLog;
import cn.com.gree.aop.annotation.Operate;
import cn.com.gree.service.UserService;
import cn.com.gree.utils.Result;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
    @DataBaseLog(operate = Operate.UPDATE,table = "t_user",module = "用户模块")
    public Result update(String userName,String password, String newPassword,String operatePassword, String newOperatePassword){
        boolean flag = false;
        if(password != null && newPassword != null){
            flag = userService.updateLoginPassword(userName,password,newPassword);
        }else if(operatePassword != null && newOperatePassword != null){
            flag = userService.updateOptionPassword(userName,operatePassword,newOperatePassword);
        }
        if(flag){
            return new Result(true,"success");
        } else {
            return new Result(false,"failed");
        }
    }
}
