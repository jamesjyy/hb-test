package cn.best.approveservice.service.impl;


import cn.best.approveservice.dao.TbSysRoleMenuMapper;
import cn.best.approveservice.entity.TbSysRoleMenu;
import cn.best.approveservice.service.TbSysRoleMenuService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


/**
 * (TbSysRoleMenu)表服务实现类
 *

 * @since 2020-06-18 11:15:59
 */
@Service("tbSysRoleMenuService")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class TbSysRoleMenuServiceImpl implements TbSysRoleMenuService {

    @Autowired
    private TbSysRoleMenuMapper tbSysRoleMenuMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 要查询的数据
     */
    @Override
    public TbSysRoleMenu getTbSysRoleMenuById(Integer id) {
        if(id==null)
            return null;
        return this.tbSysRoleMenuMapper.getTbSysRoleMenuById(id);
    }

    /**
     * 查询多条数据
     *
     * @param tbSysRoleMenu 要查询的条件
     * @param pageNum 页码
     * @param pageSize 每页数据量
     * @return 数据列表
     */
    @Override
    public List<TbSysRoleMenu> getTbSysRoleMenuListByPage(TbSysRoleMenu tbSysRoleMenu,int pageNum, int pageSize) {
        if(pageNum < 1 || pageSize < 1)
            return new ArrayList<>();
        PageHelper.startPage(pageNum,pageSize);
        return this.tbSysRoleMenuMapper.getTbSysRoleMenuList(tbSysRoleMenu);
    }

    /**
     * 新增数据
     *
     * @param tbSysRoleMenu 要添加的数据
     * @return 添加成功的数据，包含自动生成的主键id
     */
    @Override
    public int addTbSysRoleMenu(TbSysRoleMenu tbSysRoleMenu) {
        if(tbSysRoleMenu==null){
            return 0;
        }
        return this.tbSysRoleMenuMapper.addTbSysRoleMenu(tbSysRoleMenu);
    }

    /**
     * 批量添加角色菜单
     *
     * @param roleId 角色主键
     * @param menus  角色所属菜单列表
     * @return 影响数量
     */
    @Override
    public int addTbSysRoleMenuBatch(Integer roleId, Integer[] menus) {
        if(roleId==null || menus==null || menus.length<1){
            return 0;
        }
        this.tbSysRoleMenuMapper.deleteTbSysRoleMenuByRoleId(roleId);
        return this.tbSysRoleMenuMapper.addTbSysRoleMenuBatch(roleId,menus);
    }

    /**
     * 修改数据
     *
     * @param tbSysRoleMenu 要修改的数据对象，必须包含主键，否则直接返回0
     * @return 受影响数量
     */
    @Override
    public int updateTbSysRoleMenuById(TbSysRoleMenu tbSysRoleMenu) {
        if(tbSysRoleMenu==null || tbSysRoleMenu.getId() == null)
            return 0;
        return this.tbSysRoleMenuMapper.updateTbSysRoleMenuById(tbSysRoleMenu);
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 要删除的数据的主键数组
     * @return 受影响数量
     */
    @Override
    public int deleteTbSysRoleMenuById(Integer[] ids) {
        if(ids == null || ids.length<1){
            return 0;
        }
        return this.tbSysRoleMenuMapper.deleteTbSysRoleMenuById(ids);
    }

    /**
     * 根据角色id删除数据
     *
     * @param roleId 要删除的角色主键
     * @return 影响数量
     */
    @Override
    public int deleteTbSysRoleMenuByRoleId(Integer roleId) {
        if(roleId == null){
            return 0;
        }
        return this.tbSysRoleMenuMapper.deleteTbSysRoleMenuByRoleId(roleId);
    }

    @Override
    public List<TbSysRoleMenu> getTbSysRoleMenuListAll(TbSysRoleMenu tbSysRoleMenu) {
        return this.tbSysRoleMenuMapper.getTbSysRoleMenuList(tbSysRoleMenu);
    }

    @Override
    public int[] getTbSysRoleMenuMenuIdArrayAll(String roleids) {
        List<TbSysRoleMenu> list = this.tbSysRoleMenuMapper.getTbSysRoleMenuMenuIdArrayAll(roleids);
        return list.stream().mapToInt(x->x.getMenuId()).toArray();
    }
}
