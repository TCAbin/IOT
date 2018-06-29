package cn.com.gree.service;

public interface LoginService {
    /**
     * @autor 260172
     * @date 2018/6/26 15:44
     * 验证用户
     */
    boolean checkUser(String username,String password);


    /**
     * @author 260172
     * @date 2018/6/28 13:46
     * 检查设置密码
     */
    int checkOptionPassword(String username,String password);

    /**
     * @author 260172
     * @date 2018/6/28 13:46
     * 退出设置
     */
    void logoutOption(String username);
}
