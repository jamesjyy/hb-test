package cn.best.approveservice.controller;

import cn.best.approveservice.entity.UserRole;
import cn.best.approveservice.service.UserRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (UserRole)表控制层
 *
 * @author makejava
 * @since 2021-04-08 10:18:00
 */
@RestController
@RequestMapping("userRole")
public class UserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private UserRoleService userRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public UserRole selectOne(String id) {
        return this.userRoleService.queryById(id);
    }

}
