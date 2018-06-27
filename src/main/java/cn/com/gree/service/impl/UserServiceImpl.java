package cn.com.gree.service.impl;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.User;
import cn.com.gree.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("UserService")
public class UserServiceImpl implements UserService {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Override
    public boolean updateLoginPassword(String userName, String password,String newPassword) {
        if(userName == null || "".equals(userName)){
            return false;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+userName+"'").get(0);
        if(password != null && !"".equals(password) && user.getLoginPassWord().equals(password)){
            user.setLoginPassWord(newPassword);
        }else{
            return false;
        }
        baseDao.save(user);
        return true;
    }

    @Override
    public boolean updateOptionPassword(String userName, String optionPassword,String newOptionPassword) {
        if(userName == null || "".equals(userName)){
            return false;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+userName+"'").get(0);
        if(optionPassword != null && !"".equals(optionPassword) && user.getOptionPassWord().equals(optionPassword)){
            user.setOptionPassWord(newOptionPassword);
        }else{
            return false;
        }
        baseDao.save(user);
        return true;
    }
}
