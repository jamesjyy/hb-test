package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (TbSysRole)实体类
 *
 */
public class TbSysRole implements Serializable {
    private static final long serialVersionUID = 198495633306953319L;

    private Integer roleId;

    private String roleName;

    private Integer isShow;
    /**
    * 具有的权限id列表，用英文逗号分割，例如2,3,45
    */
    private String authIdList;


    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getIsShow() {
        return isShow;
    }

    public void setIsShow(Integer isShow) {
        this.isShow = isShow;
    }

    public String getAuthIdList() {
        return authIdList;
    }

    public void setAuthIdList(String authIdList) {
        this.authIdList = authIdList;
    }

}
