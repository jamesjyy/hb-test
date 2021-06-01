package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (RoleAuthority)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:58
 */
public class RoleAuthority implements Serializable {
    private static final long serialVersionUID = 239696562719423951L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 角色id
     */
    private Integer roleId;
    /**
     * 权限id
     */
    private Integer authorityId;


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

    public Integer getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(Integer authorityId) {
        this.authorityId = authorityId;
    }

}
