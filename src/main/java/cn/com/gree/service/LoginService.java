package cn.com.gree.service;

public interface LoginService {
    /**
     * @autor 260172
     * @date 2018/6/26 15:44
     * 验证用户
     */
    boolean checkUser(String userName,String password);
    /**
     * @autor 260172
     * @date 2018/6/26 15:54
     * 更新密码
     */
    boolean update(String userName,String password,String operatePassword);
}
