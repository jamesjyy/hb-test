package cn.best.approveservice.service;

import cn.best.approveservice.entity.TbSysRoleMenu;


import java.util.List;

/**
 * (TbSysRoleMenu)表服务接口
 *

 * @since 2020-06-18 11:15:59
 */
public interface TbSysRoleMenuService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 要查询的数据
     */
    TbSysRoleMenu getTbSysRoleMenuById(Integer id);

    /**
     * 查询多条数据
     *
     * @param tbSysRoleMenu 要查询的条件
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return 数据列表
     */
    List<TbSysRoleMenu> getTbSysRoleMenuListByPage(TbSysRoleMenu tbSysRoleMenu, int pageNum, int pageSize);

    List<TbSysRoleMenu> getTbSysRoleMenuListAll(TbSysRoleMenu tbSysRoleMenu);

    /**
     *
     * 获取角色所属菜单主键数组
     * @param roleids 角色集合
     * @return 角色所属菜单主键数组
     */
    int[] getTbSysRoleMenuMenuIdArrayAll(String roleids);

    /**
     * 新增数据
     *
     * @param tbSysRoleMenu 要添加的数据
     * @return 添加成功的数量
     */
    int addTbSysRoleMenu(TbSysRoleMenu tbSysRoleMenu);

    /**
     * 批量添加角色菜单
     * @param roleId 角色主键
     * @param menus 角色所属菜单列表
     * @return 影响数量
     */
    int addTbSysRoleMenuBatch(Integer roleId, Integer[] menus);

    /**
     * 修改数据
     *
     * @param tbSysRoleMenu 要修改的数据对象，必须包含主键，否则直接返回0
     * @return 受影响数量
     */
    int updateTbSysRoleMenuById(TbSysRoleMenu tbSysRoleMenu);

    /**
     * 通过主键删除数据
     *
     * @param ids 要删除的数据的主键数组
     * @return 受影响数量
     */
    int deleteTbSysRoleMenuById(Integer[] ids);

    /**
     * 根据角色id删除数据
     * @param roleId 要删除的角色主键
     * @return 影响数量
     */
    int deleteTbSysRoleMenuByRoleId(Integer roleId);

}
