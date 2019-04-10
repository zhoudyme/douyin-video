package me.zhoudongyu.service.impl;

import me.zhoudongyu.mapper.UsersMapper;
import me.zhoudongyu.pojo.Users;
import me.zhoudongyu.service.UserService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Steve
 * @date 2019/04/09
 */
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;

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
    public void saveUseer(Users users) {
        users.setId(Sid.nextShort());
        usersMapper.insert(users);
    }
}
