package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (Authority)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:55
 */
public class Authority implements Serializable {
    private static final long serialVersionUID = -31005365763026365L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 权限0:超管,1:用户,:2:子用户
     */
    private Integer describe;
    /**
     * 状态权限:0:禁用,1:启用
     */
    private Integer stasus;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDescribe() {
        return describe;
    }

    public void setDescribe(Integer describe) {
        this.describe = describe;
    }

    public Integer getStasus() {
        return stasus;
    }

    public void setStasus(Integer stasus) {
        this.stasus = stasus;
    }

}
