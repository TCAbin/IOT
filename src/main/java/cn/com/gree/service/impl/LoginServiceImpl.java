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
    public boolean checkUser(String username, String password) {
        if(username == null || "".equals(username)){
            return false;
        }
        if(password == null || "".equals(password)){
            return false;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        return password.equals(user.getLoginPassWord());
    }

    @Override
    public int checkOptionPassword(String username, String password) {
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        if(password.equals(user.getOptionPassWord())){
            if(user.isOnOption()){
                return 1;
            }
            return 0;
        }else{
            return 2;
        }
    }

    @Override
    public void logoutOption(String username) {
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        if(user.isOnOption()){
            user.setOnOption(false);
            baseDao.save(user);
        }
    }


}
