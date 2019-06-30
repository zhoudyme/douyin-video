package me.zhoudongyu.mapper;

import me.zhoudongyu.pojo.Comments;
import me.zhoudongyu.pojo.vo.CommentsVO;
import me.zhoudongyu.utils.MyMapper;

import java.util.List;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}