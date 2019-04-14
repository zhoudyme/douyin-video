package me.zhoudongyu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import me.zhoudongyu.pojo.Users;
import me.zhoudongyu.pojo.vo.UsersVO;
import me.zhoudongyu.service.UserService;
import me.zhoudongyu.utils.JSONResult;
import me.zhoudongyu.utils.MD5Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * @author Steve
 * @date 2019/04/04
 */

@RestController
@Api(value = "用户注册登录接口", tags = {"注册和登录Controller"})
public class RegistLoginController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户注册", notes = "用户注册接口")
    @PostMapping("/regist")
    public JSONResult regist(@RequestBody Users users) throws Exception {
        //1.判断用户名和密码必须不为空
        if (StringUtils.isBlank(users.getUsername()) || StringUtils.isBlank(users.getPassword())) {
            return JSONResult.errorMsg("用户名和密码不能为空");
        }
        //2.庞端用户名是否存在
        boolean userNameIsExist = userService.queryUserNameIsExist(users.getUsername());
        //3.保存
        if (!userNameIsExist) {
            users.setNickname(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFansCounts(0);
            users.setReceiveLikeCounts(0);
            users.setFollowCounts(0);
            userService.saveUseer(users);
        } else {
            return JSONResult.errorMsg("用户名已经存在，请换一个再试");
        }
        users.setPassword("");

        return JSONResult.ok(setUserRedisSessionToken(users));
    }

    private UsersVO setUserRedisSessionToken(Users users) {
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":" + users.getId(), uniqueToken, 1000 * 60 * 30);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }

    @ApiOperation(value = "用户登录", notes = "用户登录的接口")
    @PostMapping("/login")
    public JSONResult login(@RequestBody Users user) throws Exception {
        String username = user.getUsername();
        String password = user.getPassword();

        //1.判断用户名和密码必须不为空
        if (StringUtils.isBlank(user.getUsername()) || StringUtils.isBlank(user.getPassword())) {
            return JSONResult.errorMsg("用户名和密码不能为空");
        }

        //2.判断用户是否存在
        Users userResult = userService.queryUserForLogin(username, MD5Utils.getMD5Str(password));

        if (userResult != null) {
            userResult.setPassword("");
            return JSONResult.ok(setUserRedisSessionToken(userResult));
        } else {
            return JSONResult.errorMsg("用户名或密码不正确，请重试");
        }
    }

    @ApiOperation(value = "用户注销", notes = "用户注销的接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @GetMapping("/logout")
    public JSONResult logout(String userId) {
        redis.del(USER_REDIS_SESSION + ":" + userId);
        return JSONResult.ok();
    }
}
