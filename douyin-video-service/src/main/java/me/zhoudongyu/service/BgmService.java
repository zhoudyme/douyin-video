package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Bgm;

import java.util.List;

/**
 * @author Steve
 * @date 2019/04/15
 */
public interface BgmService {

    /**
     * 查询背景音乐列表
     *
     * @return List<Bgm>
     */
    public List<Bgm> queryBgmList();
}
