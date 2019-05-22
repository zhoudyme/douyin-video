package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.vo.VideosVO;
import me.zhoudongyu.utils.MyMapper;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<VideosVO> {

    List<VideosVO> queryAllVideo();
}