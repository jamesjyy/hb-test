package cn.best.approveservice.service;



import cn.best.approveservice.entity.TbSysRole;

import java.util.List;

/**
 * (TbSysRole)表服务接口
 *

 * @since 2020-06-01 14:21:35
 */
public interface TbSysRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 要查询的数据
     */
    TbSysRole getTbSysRoleById(Integer roleId);

    /**
     * 查询多条数据
     *
     * @param tbSysRole 要查询的条件
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return 数据列表
     */
    List<TbSysRole> getTbSysRoleListByPage(TbSysRole tbSysRole, int pageNum, int pageSize);

    /**
     * 新增数据
     *
     * @param tbSysRole 要添加的数据
     * @param menuIdList 关联菜单主键列表
     * @return 添加成功的数量
     */
    int addTbSysRole(TbSysRole tbSysRole, Integer[] menuIdList);

    /**
     * 修改数据
     *
     * @param tbSysRole 要修改的数据对象，必须包含主键，否则直接返回0
     * @param menuIdList 要进行关联的菜单主键数组
     * @return 受影响数量
     */
    int updateTbSysRoleById(TbSysRole tbSysRole, Integer[] menuIdList);

    /**
     * 通过主键删除数据
     *
     * @param roleIds 要删除的数据的主键数组
     * @return 受影响数量
     */
    int deleteTbSysRoleById(Integer[] roleIds);

    /**
     * 根据用户信息获取用户所属角色信息
     * @param userId 用户id
     * @return 用户所属角色信息
     */
    List<TbSysRole> getRoleByUserId(Integer userId);

    /**
     * 跟据条件获取所有角色
     * @param tbSysRole
     * @return
     */
    List<TbSysRole> getRoleByAll(TbSysRole tbSysRole);

}
