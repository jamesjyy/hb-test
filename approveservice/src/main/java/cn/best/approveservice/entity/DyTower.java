package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (DyTower)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:56
 */
public class DyTower implements Serializable {
    private static final long serialVersionUID = 626769001640254087L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 回路表id
     */
    private Integer loopid;
    /**
     * 杆塔号
     */
    private String towernum;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLoopid() {
        return loopid;
    }

    public void setLoopid(Integer loopid) {
        this.loopid = loopid;
    }

    public String getTowernum() {
        return towernum;
    }

    public void setTowernum(String towernum) {
        this.towernum = towernum;
    }

}
