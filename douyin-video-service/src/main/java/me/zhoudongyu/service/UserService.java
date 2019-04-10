package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Users;

/**
 * @author Steve
 * @date 2019/04/09
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return
     */
    public boolean queryUserNameIsExist(String username);


    /**
     * 保证用户
     *
     * @param users 用户信息
     */
    public void saveUseer(Users users);
}