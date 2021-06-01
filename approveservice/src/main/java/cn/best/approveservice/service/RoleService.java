package cn.best.approveservice.service;

import cn.best.approveservice.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * (Role)表服务接口
 *
 * @author makejava
 * @since 2021-04-08 17:27:52
 */
public interface RoleService  extends IService<Role> {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Role queryById(Integer id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Role> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role insert(Role role);

    /**
     * 修改数据
     *
     * @param role 实例对象
     * @return 实例对象
     */
    Role update(Role role);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    List<Role> selectRoleByUserId(Integer id);

    //根据用户获取角色数据
    Map<String, Object> findRoleByUserId(Integer userId);

    void saveUserRoleRealtionShip(String userId, String[] roleId);
}
