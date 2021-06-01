package cn.best.approveservice.entity;


/**
*角色用户关系表
*/
public class RoleUser {

   /*主键id*/
    private String id;

    /*角色id*/
    private Integer roleId;
    /*用户id*/
    private Integer userId;

   /****************以上主要为系统表字段********************/




 /****************以上为自己添加字段********************/

    public String getId() {
     return id;
    }

    public void setId(String id) {
     this.id = id;
    }
    public Integer getRoleId() {
     return roleId;
    }

    public void setRoleId(Integer roleId) {
     this.roleId = roleId;
    }

    public Integer getUserId() {
     return userId;
    }

    public void setUserId(Integer userId) {
     this.userId = userId;
    }
}
