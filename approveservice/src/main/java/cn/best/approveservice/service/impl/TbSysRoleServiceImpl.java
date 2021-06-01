package cn.best.approveservice.service.impl;


import cn.best.approveservice.dao.TbSysRoleMapper;
import cn.best.approveservice.entity.RoleUser;
import cn.best.approveservice.entity.TbSysRole;
import cn.best.approveservice.service.RoleUserService;
import cn.best.approveservice.service.TbSysRoleMenuService;
import cn.best.approveservice.service.TbSysRoleService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * (TbSysRole)表服务实现类
 *

 * @since 2020-06-01 14:21:35
 */
@Service
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TbSysRoleServiceImpl implements TbSysRoleService {

    @Autowired
    private TbSysRoleMapper tbSysRoleMapper;

    @Autowired
    private TbSysRoleMenuService tbSysRoleMenuService;
    @Autowired
    private RoleUserService roleUserService;
    /**
     * 通过ID查询单条数据
     *
     * @param roleId 主键
     * @return 要查询的数据
     */
    @Override
    public TbSysRole getTbSysRoleById(Integer roleId) {
        if(roleId==null)
            return null;
        return this.tbSysRoleMapper.getTbSysRoleById(roleId);
    }

    /**
     * 查询多条数据
     *
     * @param tbSysRole 要查询的条件
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return 数据列表
     */
    @Override
    public List<TbSysRole> getTbSysRoleListByPage(TbSysRole tbSysRole,int pageNum, int pageSize) {
        if(pageNum < 1 || pageSize < 1)
            return new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        return this.tbSysRoleMapper.getTbSysRoleList(tbSysRole);
    }

    @Override
    public int addTbSysRole(TbSysRole tbSysRole,Integer[] menuIdList) {
        if(tbSysRole==null){
            return 0;
        }
        int num = this.tbSysRoleMapper.addTbSysRole(tbSysRole);
        if(menuIdList!=null && menuIdList.length>0){
            this.tbSysRoleMenuService.addTbSysRoleMenuBatch(tbSysRole.getRoleId(),menuIdList);
        }
        return num;
    }

    @Override
    public int updateTbSysRoleById(TbSysRole tbSysRole,Integer[] menuIdList) {
        if(tbSysRole==null || tbSysRole.getRoleId() == null)
            return 0;
        if(menuIdList!=null){
            this.tbSysRoleMenuService.deleteTbSysRoleMenuByRoleId(tbSysRole.getRoleId());
            if(menuIdList.length > 0){
                this.tbSysRoleMenuService.addTbSysRoleMenuBatch(tbSysRole.getRoleId(),menuIdList);
            }
        }
        return this.tbSysRoleMapper.updateTbSysRoleById(tbSysRole);
    }

    /**
     * 通过主键删除数据
     *
     * @param roleIds 要删除的数据的主键数组
     * @return 受影响数量
     */
    @Override
    public int deleteTbSysRoleById(Integer[] roleIds) {
        if(roleIds == null || roleIds.length<1){
            return 0;
        }
        Arrays.stream(roleIds).forEach(id->{
            this.tbSysRoleMenuService.deleteTbSysRoleMenuByRoleId(id);
        });
        Arrays.stream(roleIds).forEach(id->{
            RoleUser roleUser=new RoleUser();
            roleUser.setRoleId(id);
            this.roleUserService.deleteRoleUser(roleUser);
        });
        return this.tbSysRoleMapper.deleteTbSysRoleById(roleIds);
    }

    /**
     * 根据用户信息获取用户所属角色信息
     *
     * @param userId 用户id
     * @return 用户所属角色信息
     */
    @Override
    public List<TbSysRole> getRoleByUserId(Integer userId) {
        if(userId==null)
            return null;
        return this.tbSysRoleMapper.getRoleByUserId(userId);
    }

    @Override
    public List<TbSysRole> getRoleByAll(TbSysRole tbSysRole) {
        return tbSysRoleMapper.getTbSysRoleList(tbSysRole);
    }
}
