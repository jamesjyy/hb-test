package cn.best.approveservice.controller;

import cn.best.approveservice.entity.Page;
import cn.best.approveservice.entity.Role;
import cn.best.approveservice.response.R;
import cn.best.approveservice.service.RoleService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * (Role)表控制层
 *
 * @author makejava
 * @since 2021-04-08 17:27:53
 */
@RestController
@RequestMapping("/admin/acl/role")
public class RoleController {
    /**
     * 服务对象
     */
    @Resource
    private RoleService roleService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("selectOne")
    public Role selectOne(Integer id) {
        return this.roleService.queryById(id);
    }

//@CrossOrigin


        @ApiOperation(value = "获取角色分页列表")
        @GetMapping("{page}/{limit}")
        public R index(
                @ApiParam(name = "page", value = "当前页码", required = true)
                @PathVariable Long page,

                @ApiParam(name = "limit", value = "每页记录数", required = true)
                @PathVariable Long limit,
                Role role) {
            Page<Role> pageParam = new Page<>(page, limit);
            QueryWrapper<Role> wrapper = new QueryWrapper<>();
            if(!StringUtils.isEmpty(role.getRoleName())) {
                wrapper.like("role_name",role.getRoleName());
            }
            roleService.page(pageParam,wrapper);
            System.out.println("++++++++++++");
            System.out.println(pageParam.getRecords());
            return R.ok().data("items", pageParam.getRecords()).data("total", pageParam.getTotal());
        }

        @ApiOperation(value = "获取角色")
        @GetMapping("get/{id}")
        public R get(@PathVariable String id) {
            Role role = roleService.getById(id);
            return R.ok().data("item", role);
        }

        @ApiOperation(value = "新增角色")
        @RequestMapping("save")
        public R save(@RequestParam ("roleName") String roleName) {
        Role role=new Role();

        role.setRoleName(roleName);
            roleService.insert(role);
            return R.ok();
        }

        @ApiOperation(value = "修改角色")
        @RequestMapping("update")
        public R updateById(@RequestParam ("roleName") String roleName,@RequestParam("id")Integer id) {
            Role role=new Role();
            role.setId(id);
            role.setRoleName(roleName);
            roleService.update(role);
            return R.ok();
        }

        @ApiOperation(value = "删除角色")
        @RequestMapping("remove/{id}")
        public R remove(@PathVariable int id) {
            roleService.removeById(id);
            return R.ok();
        }

        @ApiOperation(value = "根据id列表删除角色")
        @RequestMapping("batchRemove")
        public R batchRemove(@RequestBody List<String> idList) {
            roleService.removeByIds(idList);
            return R.ok();
        }

}
