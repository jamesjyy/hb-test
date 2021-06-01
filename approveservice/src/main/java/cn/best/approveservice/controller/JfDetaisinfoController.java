package cn.best.approveservice.controller;

import cn.best.approveservice.entity.JfDetaisinfo;
import cn.best.approveservice.service.JfDetaisinfoService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (JfDetaisinfo)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:53
 */
@RestController
@RequestMapping("jfDetaisinfo")
public class JfDetaisinfoController {
    /**
     * 服务对象
     */
    @Resource
    private JfDetaisinfoService jfDetaisinfoService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public JfDetaisinfo selectOne(Integer id) {
        return this.jfDetaisinfoService.queryById(id);
    }

}
