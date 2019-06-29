package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.vo.VideosVO;
import me.zhoudongyu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideosVO> {


    /**
     * 条件查询所有视频列表
     *
     * @param videoDesc 视频描述
     * @param userId    用户id
     * @return
     */
    List<VideosVO> queryAllVideos(@Param("videoDesc") String videoDesc,
                                  @Param("userId") String userId);

    /**
     * 查询关注的视频
     *
     * @param userId 用户id
     * @return
     */
    List<VideosVO> queryMyFollowVideos(String userId);

    /**
     * 查询点赞视频
     *
     * @param userId 用户id
     * @return
     */
    List<VideosVO> queryMyLikeVideos(@Param("userId") String userId);

    /**
     * 对视频喜欢的数量进行累加
     *
     * @param videoId 视频Id
     */
    void addVideoLikeCount(String videoId);

    /**
     * 对视频喜欢的数量进行累减
     *
     * @param videoId 视频Id
     */
    void reduceVideoLikeCount(String videoId);
}