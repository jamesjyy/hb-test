package cn.best.approveservice.dao;

import cn.best.approveservice.entity.TbSysRole;

import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * (TbSysRole)表数据库访问层
 *

 * @since 2020-06-01 14:45:00
 */
@Repository
public interface TbSysRoleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 实例对象
     */
    TbSysRole getTbSysRoleById(Integer roleId);

    /**
     * 查询指定行数据
     *
     * @param tbSysRole 要查询的数据条件
     * @return 数据列表
     */
    List<TbSysRole> getTbSysRoleList(TbSysRole tbSysRole);

    /**
     * 新增数据
     *
     * @param tbSysRole 实例对象
     * @return 影响行数
     */
    int addTbSysRole(TbSysRole tbSysRole);

    /**
     * 修改数据
     *
     * @param tbSysRole 实例对象
     * @return 影响行数
     */
    int updateTbSysRoleById(TbSysRole tbSysRole);

    /**
     * 通过主键删除数据
     *
     * @param roleIds 要删除的数据主键数组
     * @return 影响行数
     */
    int deleteTbSysRoleById(Integer[] roleIds);

    /**
     * 根据用户信息获取用户所属角色信息
     * @param userId 用户id
     * @return 用户所属角色信息
     */
    List<TbSysRole> getRoleByUserId(int userId);

}
