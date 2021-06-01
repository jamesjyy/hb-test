package cn.best.approveservice.controller;

import cn.best.approveservice.entity.Proinfo;
import cn.best.approveservice.service.ProinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Proinfo)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
@RestController
@RequestMapping("proinfo")
public class ProinfoController {
    /**
     * 服务对象
     */
    @Resource
    private ProinfoService proinfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Proinfo selectOne(Integer id) {
        return this.proinfoService.queryById(id);
    }

}
