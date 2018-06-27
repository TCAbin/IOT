package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.User;
import cn.com.gree.service.LoginService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("LoginService")
public class LoginServiceImpl implements LoginService {
    @Resource(name = "BaseDao")
    private BaseDao baseDao;


    @Override
    public boolean checkUser(String userName, String password) {
        if(userName == null || "".equals(userName)){
            return false;
        }
        if(password == null || "".equals(password)){
            return false;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+userName+"'").get(0);
        return password.equals(user.getLoginPassWord());
    }


}
