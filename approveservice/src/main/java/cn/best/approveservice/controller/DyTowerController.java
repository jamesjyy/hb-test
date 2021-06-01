package cn.best.approveservice.controller;

import cn.best.approveservice.entity.DyTower;
import cn.best.approveservice.service.DyTowerService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DyTower)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
@RestController
@RequestMapping("dyTower")
public class DyTowerController {
    /**
     * 服务对象
     */
    @Resource
    private DyTowerService dyTowerService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DyTower selectOne(Integer id) {
        return this.dyTowerService.queryById(id);
    }

}
