package me.zhoudongyu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import me.zhoudongyu.pojo.Users;
import me.zhoudongyu.pojo.UsersReport;
import me.zhoudongyu.pojo.vo.PublishVideo;
import me.zhoudongyu.pojo.vo.UsersVO;
import me.zhoudongyu.service.UserService;
import me.zhoudongyu.utils.JSONResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * @author Steve
 * @date 2019/04/04
 */

@RestController
@Api(value = "用户相关业务接口", tags = {"用户相关业务Controller"})
@RequestMapping("/user")
public class UserController extends BasicController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "用户上传头像", notes = "根据用户id上传头像接口")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true,
            dataType = "String", paramType = "query")
    @PostMapping("/uploadFace")
    public JSONResult uploadFace(String userId, @RequestParam("file") MultipartFile[] files) throws Exception {

        String fileSpace = "fileSpace";

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        String uploadPathDb = "/" + userId + "/face";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        try {
            if (files != null && files.length > 0) {

                String fileName = files[0].getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {

                    //文件上传的最终保存路径
                    String finalFacePath = fileSpace + uploadPathDb + "/" + fileName;
                    //设置数据库保存的路径
                    uploadPathDb += ("/" + fileName);

                    File outFile = new File(finalFacePath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                }
            } else {
                return JSONResult.errorMsg("上传出错...");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.errorMsg("上传出错...");
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.flush();
                fileOutputStream.close();
            }
        }

        Users user = new Users();
        user.setId(userId);
        user.setFaceImage(uploadPathDb);
        userService.updateUserInfo(user);
        return JSONResult.ok(uploadPathDb);
    }

    @ApiOperation(value = "查询用户信息", notes = "根据用户id、粉丝id查询用户信息接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = false,
                    dataType = "String", paramType = "query")
    })
    @GetMapping("/query")
    public JSONResult query(String userId, String fanId) {

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }
        Users userInfo = userService.queryUserInfo(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(userInfo, usersVO);


        usersVO.setFollow(userService.queryIfFollow(userId, fanId));
        return JSONResult.ok(usersVO);
    }

    @ApiOperation(value = "查询视频发布者和视频视频点赞信息", notes = "根据登录用户id、视频id和视频发布者id来查询视频发布者和视频视频点赞信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "loginUserId", value = "登录用户id", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "videoId", value = "视频id", required = false,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "publishUserId", value = "视频发布者id", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("/queryPublisher")
    public JSONResult queryPublisher(String loginUserId, String videoId,
                                     String publishUserId) {

        if (StringUtils.isBlank(publishUserId)) {
            return JSONResult.errorMsg("发布者Id不能为空");
        }

        //1、查询视频发布者的信息
        Users userInfo = userService.queryUserInfo(publishUserId);
        UsersVO publisher = new UsersVO();
        BeanUtils.copyProperties(userInfo, publisher);

        //2、查询当前登陆者和视频的点赞关系
        boolean userLikeVideo = userService.isUserLikeVideo(loginUserId, videoId);

        PublishVideo bean = new PublishVideo();
        bean.setPublisher(publisher);
        bean.setUserLikeVideo(userLikeVideo);

        return JSONResult.ok(bean);
    }


    @ApiOperation(value = "关注用户", notes = "粉丝关注用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("/beYourFans")
    public JSONResult beYourFans(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("用户id或粉丝id不能为空...");
        }
        userService.saveUserFanRelation(userId, fanId);
        return JSONResult.ok("关注成功");
    }

    @ApiOperation(value = "取消关注用户", notes = "粉丝取消关注用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "fanId", value = "粉丝id", required = true,
                    dataType = "String", paramType = "query")
    })
    @PostMapping("/dontBeYourFans")
    public JSONResult dontBeYourFans(String userId, String fanId) {

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(fanId)) {
            return JSONResult.errorMsg("用户id或粉丝id不能为空...");
        }
        userService.deleteUserFanRelation(userId, fanId);
        return JSONResult.ok("取消关注成功");
    }

    @ApiOperation(value = "用户举报视频", notes = "粉丝取消关注用户")
    @PostMapping("/reportUser")
    public JSONResult reportUser(@RequestBody UsersReport usersReport) {

        // 保存举报信息
        userService.reportUser(usersReport);

        return JSONResult.errorMsg("举报成功...有你平台变得更美好...");
    }

}
