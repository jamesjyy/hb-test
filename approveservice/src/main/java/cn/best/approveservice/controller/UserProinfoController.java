package cn.best.approveservice.controller;

import cn.best.approveservice.entity.UserProinfo;
import cn.best.approveservice.service.UserProinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserProinfo)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:18:00
 */
@RestController
@RequestMapping("userProinfo")
public class UserProinfoController {
    /**
     * 服务对象
     */
    @Resource
    private UserProinfoService userProinfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserProinfo selectOne(Integer id) {
        return this.userProinfoService.queryById(id);
    }

}
