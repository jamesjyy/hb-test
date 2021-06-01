package cn.best.approveservice.controller;

import cn.best.approveservice.entity.Authority;
import cn.best.approveservice.service.AuthorityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Authority)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
@RestController
@RequestMapping("authority")
public class AuthorityController {
    /**
     * 服务对象
     */
    @Resource
    private AuthorityService authorityService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Authority selectOne(Integer id) {
        return this.authorityService.queryById(id);
    }

}
