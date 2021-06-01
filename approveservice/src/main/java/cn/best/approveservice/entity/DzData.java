package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (DzData)实体类
 *
 * @author makejava
 * @since 2021-04-25 17:27:04
 */
public class DzData implements Serializable {
    private static final long serialVersionUID = -92153287362769566L;
    /**
     * id
     */
    private Integer id;
    /**
     * 杆塔号
     */
    private String towernum;
    /**
     * 相别
     */
    private String phase;
    /**
     * 线路
     */
    private String loopidtime;
    /**
     * 该相检测结果
     */
    private String result;
    /**
     * 检测时间
     */
    private String time;
    /**
     * 用户表id
     */
    private String userid;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTowernum() {
        return towernum;
    }

    public void setTowernum(String towernum) {
        this.towernum = towernum;
    }

    public String getPhase() {
        return phase;
    }

    public void setPhase(String phase) {
        this.phase = phase;
    }

    public String getLoopidtime() {
        return loopidtime;
    }

    public void setLoopidtime(String loopidtime) {
        this.loopidtime = loopidtime;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

}
