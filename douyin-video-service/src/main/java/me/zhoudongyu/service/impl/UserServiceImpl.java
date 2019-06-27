package me.zhoudongyu.service.impl;

import me.zhoudongyu.mapper.UsersFansMapper;
import me.zhoudongyu.mapper.UsersLikeVideosMapper;
import me.zhoudongyu.mapper.UsersMapper;
import me.zhoudongyu.pojo.Users;
import me.zhoudongyu.pojo.UsersFans;
import me.zhoudongyu.pojo.UsersLikeVideos;
import me.zhoudongyu.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.List;

/**
 * @author Steve
 * @date 2019/04/09
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired(required = false)
    private UsersMapper usersMapper;

    @Autowired(required = false)
    private UsersLikeVideosMapper usersLikeVideosMapper;

    @Autowired(required = false)
    private UsersFansMapper usersFansMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUserNameIsExist(String username) {
        Users users = new Users();
        users.setUsername(username);
        Users result = usersMapper.selectOne(users);
        return result == null ? false : true;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUser(Users users) {
        users.setId(Sid.nextShort());
        usersMapper.insert(users);
    }

    @Override
    public Users queryUserForLogin(String username, String password) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("username", username);
        criteria.andEqualTo("password", password);
        Users result = usersMapper.selectOneByExample(userExample);
        return result;
    }

    @Override
    public void updateUserInfo(Users user) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", user.getId());
        usersMapper.updateByExampleSelective(user, userExample);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserInfo(String userId) {
        Example userExample = new Example(Users.class);
        Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", userId);
        Users user = usersMapper.selectOneByExample(userExample);
        return user;
    }

    @Override
    public boolean isUserLikeVideo(String userId, String videoId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return false;
        }

        Example example = new Example(UsersLikeVideos.class);
        Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("videoId", videoId);
        List<UsersLikeVideos> usersLikeVideosList = usersLikeVideosMapper.selectByExample(example);
        return null != usersLikeVideosList && usersLikeVideosList.size() > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveUserFanRelation(String userId, String fanId) {
        UsersFans usersFan = new UsersFans();
        String relId = Sid.nextShort();
        usersFan.setId(relId);
        usersFan.setUserId(userId);
        usersFan.setFanId(fanId);
        usersFansMapper.insert(usersFan);

        usersMapper.addFansCount(userId);
        usersMapper.addFollowerCount(fanId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteUserFanRelation(String userId, String fanId) {
        Example example = new Example(UsersFans.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        usersFansMapper.deleteByExample(example);
        usersMapper.reduceFansCount(userId);
        usersMapper.reduceFollowerCount(fanId);

    }

    @Override
    public boolean queryIfFollow(String userId, String fanId) {

        Example example = new Example(UsersFans.class);
        Criteria criteria = example.createCriteria();

        criteria.andEqualTo("userId", userId);
        criteria.andEqualTo("fanId", fanId);

        List<UsersFans> usersFansList = usersFansMapper.selectByExample(example);
        return null != usersFansList && !usersFansList.isEmpty();
    }

}
