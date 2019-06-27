package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.Users;
import me.zhoudongyu.utils.MyMapper;

public interface UsersMapper extends MyMapper<Users> {

    /**
     * 用户受喜欢数累加
     *
     * @param userId 用户Id
     */
    void addReceiveLikeCount(String userId);

    /**
     * 用户受喜欢数累减
     *
     * @param userId 用户Id
     */
    void reduceReceiveLikeCount(String userId);

    /**
     * 增加粉丝数
     *
     * @param userId 用户id
     */
    void addFansCount(String userId);

    /**
     * 减少粉丝数
     *
     * @param userId 用户id
     */
    void reduceFansCount(String userId);

    /**
     * 增加关注数
     *
     * @param userId 用户id
     */
    void addFollowerCount(String userId);

    /**
     * 减少关注数
     *
     * @param userId 用户id
     */
    void reduceFollowerCount(String userId);


}