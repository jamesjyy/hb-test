package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (UserProinfo)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:59
 */
public class UserProinfo implements Serializable {
    private static final long serialVersionUID = -81371429040297962L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 产品串号
     */
    private String ime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

}
