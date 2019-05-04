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
    void saveVideo(Videos video);
}
