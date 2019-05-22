package me.zhoudongyu.controller;

import io.swagger.annotations.*;
import me.zhoudongyu.enums.VideoStatusEnum;
import me.zhoudongyu.pojo.Bgm;
import me.zhoudongyu.pojo.Videos;
import me.zhoudongyu.service.BgmService;
import me.zhoudongyu.service.VideoService;
import me.zhoudongyu.utils.FetchVideoCover;
import me.zhoudongyu.utils.JSONResult;
import me.zhoudongyu.utils.MergeVideoMp3;
import me.zhoudongyu.utils.PagedResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

/**
 * @author Steve
 * @date 2019/04/27
 */

@RestController
@Api(value = "视频相关业务接口", tags = {"视频相关业务Controller"})
@RequestMapping("/video")
public class VideoController extends BasicController {

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    @ApiOperation(value = "上传视频", notes = "上传视频的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bgmId", value = "背景音乐id", required = false,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds", value = "背景音乐播放长度", required = true,
                    dataType = "double", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth", value = "视频宽度", required = true,
                    dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight", value = "视频高度", required = true,
                    dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "desc", value = "视频描述", required = false,
                    dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/upload", headers = "content-type=multipart/form-data")
    public JSONResult upload(String userId, String bgmId, double videoSeconds, int videoWidth, int videoHeight, String desc,
                             @ApiParam(value = "短视频", required = true) MultipartFile file) throws Exception {

        String fileSpace = "fileSpace";

        if (StringUtils.isBlank(userId)) {
            return JSONResult.errorMsg("用户id不能为空...");
        }

        String uploadPathDb = fileSpace + "/" + userId + "/video";
        String coverPathDb = fileSpace + "/" + userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        String finalVideoPath = "";
        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                String fileNamePrefix = fileName.split("\\.")[0];


                if (StringUtils.isNotBlank(fileName)) {

                    //文件上传的最终保存路径
                    finalVideoPath = uploadPathDb + "/" + fileName;

                    coverPathDb = coverPathDb + "/" + fileNamePrefix + ".jpg";

                    File outFile = new File(finalVideoPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
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

        //判断bgmId是否为空，如果不为空，
        //那就查询bgm的信息，并且合并视频，生产新的视频
        if (StringUtils.isNotBlank(bgmId)) {
            Bgm bgm = bgmService.queryBgmById(bgmId);
            String mp3InputPath = bgm.getPath();

            MergeVideoMp3 tool = new MergeVideoMp3(FFPEG_EXE);
            String videoInputPath = finalVideoPath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";

            uploadPathDb += "/" + videoOutputName;
            tool.converetor(videoInputPath, mp3InputPath, videoSeconds, uploadPathDb);
        }

        //对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFPEG_EXE);
        videoInfo.getCover(uploadPathDb, coverPathDb);


        //保存视频信息到数据库
        Videos video = new Videos();
        video.setAudioId(bgmId);
        video.setUserId(userId);
        video.setVideoSeconds((float) videoSeconds);
        video.setVideoHeight(videoHeight);
        video.setVideoWidth(videoWidth);
        video.setVideoDesc(desc);
        video.setVideoPath(uploadPathDb);
        video.setCoverPath(coverPathDb);
        video.setStatus(VideoStatusEnum.SUCCESS.value);
        video.setCreateTime(new Date());
        String videoId = videoService.saveVideo(video);
        return JSONResult.ok(videoId);
    }


    @ApiOperation(value = "上传封面", notes = "上传封面的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户id", required = true,
                    dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId", value = "视频主键id", required = true,
                    dataType = "String", paramType = "form")

    })
    @PostMapping(value = "/uploadCover", headers = "content-type=multipart/form-data")
    public JSONResult uploadCover(String userId, String videoId,
                                  @ApiParam(value = "视频封面", required = true) MultipartFile file) throws Exception {

        String fileSpace = "fileSpace";

        if (StringUtils.isBlank(userId) || StringUtils.isBlank(videoId)) {
            return JSONResult.errorMsg("用户id和视频主键id不能为空...");
        }

        String uploadPathDb = fileSpace + "/" + userId + "/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        String finalCoverPath = "";
        try {
            if (file != null) {

                String fileName = file.getOriginalFilename();
                if (StringUtils.isNotBlank(fileName)) {

                    //文件上传的最终保存路径
                    finalCoverPath = uploadPathDb + "/" + fileName;

                    File outFile = new File(finalCoverPath);
                    if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                        //创建父文件夹
                        outFile.getParentFile().mkdirs();
                    }

                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
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

        videoService.updateVideo(videoId, uploadPathDb);
        return JSONResult.ok();
    }


    @ApiOperation(value = "获取视频列表", notes = "获取视频列表的接口")
    @GetMapping("showAll")
    public JSONResult showAll(Integer page) throws Exception {

        if (null == page) {
            page = 1;
        }

        PagedResult result = videoService.getAllVideos(page, PAGE_SIZE);
        return JSONResult.ok(result);
    }
}
