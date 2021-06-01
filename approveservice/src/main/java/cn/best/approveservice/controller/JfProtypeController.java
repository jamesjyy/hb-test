package cn.best.approveservice.controller;

import cn.best.approveservice.entity.JfProtype;
import cn.best.approveservice.service.JfProtypeService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (JfProtype)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:58
 */
@RestController
@RequestMapping("jfProtype")
public class JfProtypeController {
    /**
     * 服务对象
     */
    @Resource
    private JfProtypeService jfProtypeService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JfProtype selectOne(Integer id) {
        return this.jfProtypeService.queryById(id);
    }

}
