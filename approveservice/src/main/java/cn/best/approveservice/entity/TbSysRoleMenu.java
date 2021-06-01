package cn.best.approveservice.entity;

import java.io.Serializable;


public class TbSysRoleMenu implements Serializable {
    private static final long serialVersionUID = 997663801421822414L;

    private Integer id;
    /**
    * 角色表主键
    */
    private Integer roleId;
    /**
    * 菜单表主键
    */
    private Integer menuId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

}
