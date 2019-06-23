package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Videos;
import me.zhoudongyu.utils.PagedResult;

import java.util.List;

/**
 * 视频Service
 *
 * @author Steve
 * @date 2019/05/04
 */
public interface VideoService {

    /**
     * 保存视频信息
     *
     * @param video 视频信息
     */
    String saveVideo(Videos video);


    /**
     * 修改视频的封面
     *
     * @param videoId   视频id
     * @param coverPath 封面路径
     */
    void updateVideo(String videoId, String coverPath);

    /**
     * 分页查询视频列表
     *
     * @param page     页数
     * @param pageSize 分页大小
     * @return 视频列表
     */
    PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
     * 获取热搜词列表
     *
     * @return 热搜词列表
     */
    List<String> getGotWords();

    /**
     * 用户喜欢/点赞视频
     *
     * @param userId         用户Id
     * @param videoId        视频Id
     * @param videoCreatorId 视频发布者Id
     */
    void userLikeVideo(String userId, String videoId, String videoCreatorId);


    /**
     * 用户不喜欢/取消点赞视频
     *
     * @param userId         用户Id
     * @param videoId        视频Id
     * @param videoCreatorId 视频发布者Id
     */

    void userUnLikeVideo(String userId, String videoId, String videoCreatorId);
}
