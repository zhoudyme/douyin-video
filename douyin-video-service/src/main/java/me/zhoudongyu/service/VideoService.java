package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Videos;
import me.zhoudongyu.utils.PagedResult;

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
    PagedResult getAllVideos(Integer page, Integer pageSize);
}
