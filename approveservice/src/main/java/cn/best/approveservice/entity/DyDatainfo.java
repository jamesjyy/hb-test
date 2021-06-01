package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (DyDatainfo)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:54
 */
public class DyDatainfo implements Serializable {
    private static final long serialVersionUID = -98780411019935936L;
    /**
     * 主键id
     */
    private Integer id;
    /**
     * 线路名称
     */
    private String loopname;
    /**
     * 杆塔号
     */
    private String towernum;
    /**
     * 相别
     */
    private String phase;
    /**
     * 串号
     */
    private Integer strand;
    /**
     * 一串的检测结果
     */
    private String result;
    /**
     * 检查时间
     */
    private String time;
    /**
     * 用户id
     */
    private String userid;
    /**
     * 频率
     */
    private String rcf;

    private String searchDate;

    private Integer towernumbercount;

    private Integer towerstatus;


    public Integer getTowernumbercount() {
        return towernumbercount;
    }

    public void setTowernumbercount(Integer towernumbercount) {
        this.towernumbercount = towernumbercount;
    }

    public Integer getTowerstatus() {
        return towerstatus;
    }

    public void setTowerstatus(Integer towerstatus) {
        this.towerstatus = towerstatus;
    }

    public String getSearchDate() {
        return searchDate;
    }

    public void setSearchDate(String searchDate) {
        this.searchDate = searchDate;
    }

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

    public Integer getStrand() {
        return strand;
    }

    public void setStrand(Integer strand) {
        this.strand = strand;
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

    public String getRcf() {
        return rcf;
    }

    public void setRcf(String rcf) {
        this.rcf = rcf;
    }

}
