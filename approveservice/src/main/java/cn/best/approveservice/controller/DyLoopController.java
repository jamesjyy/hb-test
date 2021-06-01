package cn.best.approveservice.controller;

import cn.best.approveservice.entity.DyLoop;
import cn.best.approveservice.service.DyLoopService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (DyLoop)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
@RestController
@RequestMapping("dyLoop")
public class DyLoopController {
    /**
     * 服务对象
     */
    @Resource
    private DyLoopService dyLoopService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public DyLoop selectOne(Integer id) {
        return this.dyLoopService.queryById(id);
    }

}
