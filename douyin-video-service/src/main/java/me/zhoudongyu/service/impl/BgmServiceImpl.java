package me.zhoudongyu.service.impl;

import me.zhoudongyu.mapper.BgmMapper;
import me.zhoudongyu.pojo.Bgm;
import me.zhoudongyu.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Steve
 * @date 2019/04/15
 */
@Service
public class BgmServiceImpl implements BgmService {

    @Autowired
    private BgmMapper bgmMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Bgm> queryBgmList() {
        return bgmMapper.selectAll();
    }
}
