package cn.com.gree.filter;

import cn.com.gree.dao.BaseDao;
import cn.com.gree.entity.User;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@Component
public class MySessionListener implements HttpSessionListener {

    @Resource(name = "BaseDao")
    private BaseDao baseDao;

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {

    }

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        HttpSession session = httpSessionEvent.getSession();
        String username = String.valueOf(session.getAttribute("user"));
        if(username == null || "".equals(username) || "null".equals(username)){
            return;
        }
        User user = (User) baseDao.getByJpql("select o from User o where o.userName = '"+username+"'").get(0);
        if(user.isOnOption()){
            user.setOnOption(false);
            baseDao.save(user);
        }
    }
}
