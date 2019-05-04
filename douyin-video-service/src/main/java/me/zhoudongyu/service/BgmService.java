package me.zhoudongyu.service;

import me.zhoudongyu.pojo.Bgm;

import java.util.List;

/**
 * 背景音乐Service
 *
 * @author Steve
 * @date 2019/04/15
 */
public interface BgmService {

    /**
     * 查询背景音乐列表
     *
     * @return 背景音乐集合
     */
    List<Bgm> queryBgmList();

    /**
     * 根据id查询bgm信息
     *
     * @param bgmId 背景音乐Id
     * @return 背景音乐
     */
    public Bgm queryBgmById(String bgmId);
}
