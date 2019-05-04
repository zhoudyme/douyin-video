package me.zhoudongyu.service.impl;

import me.zhoudongyu.mapper.VideosMapper;
import me.zhoudongyu.pojo.Videos;
import me.zhoudongyu.service.VideoService;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Steve
 * @date 2019/05/04
 */
@Service
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideosMapper videosMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void saveVideo(Videos video) {
        String id = Sid.nextShort();
        video.setId(id);
        videosMapper.insertSelective(video);
    }
}
