package cn.com.gree.filter;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.User;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class MySessionListener implements HttpSessionListener {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        User user = (User) baseDao.getByJpql(" select u from User u where u.userName = 'admin' ").get(0);
        if(user.isOnOption()){
            user.setOnOption(false);
            baseDao.save(user);
        }
    }
}
