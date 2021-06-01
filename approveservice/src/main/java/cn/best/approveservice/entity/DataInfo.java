package cn.best.approveservice.entity;

import lombok.Data;

@Data
public class DataInfo {
    private Integer id;//主键id
    private String loopid; //线路
    private String towernum; //杆塔号
    private String phase; //相别
    private Integer strand;  //串号
    private String result; //一串的检测结果
    private String time; //	检查时间
    private String userid; //用户id

    public DataInfo(Integer id, String loopid, String towernum, String phase, Integer strand, String result, String time, String userid) {
        this.id = id;
        this.loopid = loopid;
        this.towernum = towernum;
        this.phase = phase;
        this.strand = strand;
        this.result = result;
        this.time = time;
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLoopid() {
        return loopid;
    }

    public void setLoopid(String loopid) {
        this.loopid = loopid;
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
}
