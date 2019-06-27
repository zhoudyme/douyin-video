package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Users;

/**
 * 用户Service
 *
 * @author Steve
 * @date 2019/04/09
 */
public interface UserService {

    /**
     * 判断用户名是否存在
     *
     * @param username 用户名
     * @return 是否存在
     */
    boolean queryUserNameIsExist(String username);


    /**
     * 保存用户
     *
     * @param users 用户信息
     */
    void saveUser(Users users);


    /**
     * 通过用户名和密码查询用户
     *
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    Users queryUserForLogin(String username, String password);

    /**
     * 用户修改信息
     *
     * @param users 用户信息
     */
    void updateUserInfo(Users users);

    /**
     * 查询用户信息
     *
     * @param userId 用户id
     * @return 用户信息
     */
    Users queryUserInfo(String userId);

    /**
     * 查询用户是否喜欢视频
     *
     * @param userId  用户Id
     * @param videoId 视频Id
     * @return 是否喜欢
     */
    boolean isUserLikeVideo(String userId, String videoId);

    /**
     * 增加用户和粉丝的关系
     *
     * @param userId
     * @param fanId
     */
    void saveUserFanRelation(String userId, String fanId);

    /**
     * 删除用户和粉丝的关系
     *
     * @param userId
     * @param fanId
     */
    void deleteUserFanRelation(String userId, String fanId);


    /**
     * 查询用户是否关注
     *
     * @param userId 用户id
     * @param fanId  粉丝id
     * @return
     */
    boolean queryIfFollow(String userId, String fanId);
}
