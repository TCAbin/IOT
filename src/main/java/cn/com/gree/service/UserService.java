package cn.com.gree.service;

public interface UserService {

    /**
     * @autor 260172
     * @date 2018/6/26 15:54
     * 更新登录密码
     */
    int updateLoginPassword(String userName,String password,String newPassword);

    /**
     * @author 260172
     * @date 2018/6/27 11:18
     * 更新设置密码
     */
    int updateOptionPassword(String userName,String optionPassword,String newOptionPassword);

}
