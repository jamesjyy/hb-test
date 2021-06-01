package cn.best.approveservice.controller;

import cn.best.approveservice.entity.RoleAuthority;
import cn.best.approveservice.service.RoleAuthorityService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (RoleAuthority)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
@RestController
@RequestMapping("roleAuthority")
public class RoleAuthorityController {
    /**
     * 服务对象
     */
    @Resource
    private RoleAuthorityService roleAuthorityService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public RoleAuthority selectOne(Integer id) {
        return this.roleAuthorityService.queryById(id);
    }

}
