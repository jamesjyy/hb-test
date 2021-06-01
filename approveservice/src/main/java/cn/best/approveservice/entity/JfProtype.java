package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (JfProtype)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
public class JfProtype implements Serializable {
    private static final long serialVersionUID = 131406795590979230L;
    /**
     * 设备id
     */
    private Integer id;
    /**
     * 设备名称
     */
    private String typename;
    /**
     * 设备状态
     */
    private String status;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypename() {
        return typename;
    }

    public void setTypename(String typename) {
        this.typename = typename;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
