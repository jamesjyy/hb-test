package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (DyLoop)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
public class DyLoop implements Serializable {
    private static final long serialVersionUID = 186615371057780496L;
    /**
     * 回路表主键id
     */
    private Integer id;
    /**
     * 回路名称
     */
    private String loopname;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoopname() {
        return loopname;
    }

    public void setLoopname(String loopname) {
        this.loopname = loopname;
    }

}
