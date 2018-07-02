package cn.com.gree.controller;

import cn.com.gree.aop.annotation.DataBaseLog;
import cn.com.gree.aop.annotation.Operate;
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
    @DataBaseLog(operate = Operate.UPDATE,table = "t_user",module = "用户模块")
    public Result update(HttpServletRequest request,String password, String newPassword,String operatePassword, String newOperatePassword){
        int flag = 0;
        String username = String.valueOf(request.getSession().getAttribute("user"));
        if(password != null && newPassword != null){
            flag = userService.updateLoginPassword(username,password,newPassword);
        }else if(operatePassword != null && newOperatePassword != null){
            flag = userService.updateOptionPassword(username,operatePassword,newOperatePassword);
        }
        if(flag == 1){
            return new Result(false,"1");
        } else if (flag == 2) {
            return new Result(false,"2"); // 原密码错误
        }else {
            return new Result(true,"success");
        }
    }

    /**
     * @author Abin
     * @date 2018/6/30 14:59
     * 检测session
     */
    @RequestMapping(value = "checkSession")
    public Result checkSession(HttpServletRequest request){
        return new Result(true,"success");
    }

}
