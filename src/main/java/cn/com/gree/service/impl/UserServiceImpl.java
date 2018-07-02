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
    public int updateLoginPassword(String username, String password,String newPassword) {
        if(username == null || "".equals(username) || "null".equals(username)){
            return 1; // 用户名不能为空
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        if(password != null && !"".equals(password) && user.getLoginPassWord().equals(password)){
            user.setLoginPassWord(newPassword);
        }else{
            return 2; // 原密码错误
        }
        baseDao.save(user);
        return 0;
    }

    @Override
    public int updateOptionPassword(String username, String optionPassword,String newOptionPassword) {
        if(username == null || "".equals(username) || "null".equals(username)){
            return 1;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        if(optionPassword != null && !"".equals(optionPassword) && user.getOptionPassWord().equals(optionPassword)){
            user.setOptionPassWord(newOptionPassword);
        }else{
            return 2;
        }
        baseDao.save(user);
        return 0;
    }
}
