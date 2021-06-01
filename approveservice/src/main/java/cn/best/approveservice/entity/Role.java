package cn.best.approveservice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.Date;
import java.io.Serializable;
import java.util.List;

/**
 * (Role)实体类
 *
 * @author makejava
 * @since 2021-04-08 17:27:52
 */

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("role")
@ApiModel(value="Role对象", description="")
public class Role implements Serializable {
    private static final long serialVersionUID = 122277533234166467L;
    /**
     * 主键
     */
    @ApiModelProperty(value = "角色id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private Integer id;
    /**
     * 角色0:超管,1:用户,2:子用户
     */
    private String role;
    /**
     * 状态:0:启用,1:禁用
     */
    private String status;
    /**
     * 创建时间
     */
    private String creationTime;
    /**
     * 更新时间
     */
    private String updateTime;
    @ApiModelProperty(value = "角色名称")
    private String roleName;



    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(String creationTime) {
        this.creationTime = creationTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

}
