package me.zhoudongyu.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.zhoudongyu.service.BgmService;
import me.zhoudongyu.utils.JSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Steve
 * @date 2019/04/15
 */

@RestController
@Api(value = "背景音乐业务的接口", tags = {"背景音乐业务Controller"})
@RequestMapping("/bgm")
public class BgmController {

    @Autowired
    private BgmService bgmService;

    @ApiOperation(value = "获取背景音乐列表", notes = "获取背景音乐列表的接口")
    @GetMapping("/list")
    public JSONResult list() {
        return JSONResult.ok(bgmService.queryBgmList());
    }
}
