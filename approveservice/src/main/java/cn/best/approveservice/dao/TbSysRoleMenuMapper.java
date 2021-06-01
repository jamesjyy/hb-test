package cn.best.approveservice.dao;


import cn.best.approveservice.entity.TbSysRoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (TbSysRoleMenu)表数据库访问层
 *

 * @since 2020-06-18 11:15:59
 */
@Repository
public interface TbSysRoleMenuMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TbSysRoleMenu getTbSysRoleMenuById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param tbSysRoleMenu 要查询的数据条件
     * @return 数据列表
     */
    List<TbSysRoleMenu> getTbSysRoleMenuList(TbSysRoleMenu tbSysRoleMenu);

    /**
     * 新增数据
     *
     * @param tbSysRoleMenu 实例对象
     * @return 影响行数
     */
    int addTbSysRoleMenu(TbSysRoleMenu tbSysRoleMenu);

    /**
     *
     * 批量添加角色菜单关联表数据
     * @param roleId 角色主键
     * @param menuIds 菜单主键列表
     * @return 影响数量
     */
    int addTbSysRoleMenuBatch(@Param("roleId") Integer roleId, @Param("menuIds") Integer[] menuIds);

    /**
     * 修改数据
     *
     * @param tbSysRoleMenu 实例对象
     * @return 影响行数
     */
    int updateTbSysRoleMenuById(TbSysRoleMenu tbSysRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param ids 要删除的数据主键数组
     * @return 影响行数
     */
    int deleteTbSysRoleMenuById(Integer[] ids);

    /**
     * 根据角色id删除数据
     * @param roleId 要删除的角色主键
     * @return 影响数量
     */
    int deleteTbSysRoleMenuByRoleId(Integer roleId);

    /**
     * 查询所有角色的MenuId
     * @param roleids 角色id字符串，逗号拼接
     * @return
     */
    List<TbSysRoleMenu> getTbSysRoleMenuMenuIdArrayAll(String roleids);
}
