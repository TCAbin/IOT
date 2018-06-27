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
    public boolean update(String userName, String password, String operatePassword) {
        if(userName == null || "".equals(userName)){
            return false;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+userName+"'").get(0);
        if(password != null && !"".equals(password)){
            user.setLoginPassWord(password);
        }
        if(operatePassword != null && !"".equals(operatePassword)){
            user.setOptionPassWord(operatePassword);
        }
        baseDao.save(user);
        return true;
    }
}
