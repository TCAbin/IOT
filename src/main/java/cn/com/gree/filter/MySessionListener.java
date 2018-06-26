package cn.com.gree.filter;

import cn.com.gree.dao.BaseDao;

import javax.annotation.Resource;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSession;
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
        HttpSession session = httpSessionEvent.getSession();
        String jpql = " update User u set u.isOnOption = false ";
        baseDao.executeJpql(jpql);

    }
}
