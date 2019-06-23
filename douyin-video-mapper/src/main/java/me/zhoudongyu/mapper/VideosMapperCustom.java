package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.vo.VideosVO;
import me.zhoudongyu.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideosVO> {

    List<VideosVO> queryAllVideo(@Param("videoDesc") String videoDesc);

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