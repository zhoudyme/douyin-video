package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Videos;

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
}
