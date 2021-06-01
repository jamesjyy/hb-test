package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (JfDetaisinfo)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:52
 */
public class JfDetaisinfo implements Serializable {
    private static final long serialVersionUID = -81649617849805784L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 线路名称
     */
    private String line;
    /**
     * 线路编号
     */
    private String linenum;
    /**
     * 检测人员
     */
    private String checkuser;
    /**
     * 输入日期
     */
    private String ctime;
    /**
     * 编号照片
     */
    private String numpic;
    /**
     * 整体照片
     */
    private String allpic;
    /**
     * 不良照片
     */
    private String badpic;
    /**
     * 线路等级
     */
    private String linelevel;
    /**
     * 检测距离
     */
    private String distance;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getLinenum() {
        return linenum;
    }

    public void setLinenum(String linenum) {
        this.linenum = linenum;
    }

    public String getCheckuser() {
        return checkuser;
    }

    public void setCheckuser(String checkuser) {
        this.checkuser = checkuser;
    }

    public String getCtime() {
        return ctime;
    }

    public void setCtime(String ctime) {
        this.ctime = ctime;
    }

    public String getNumpic() {
        return numpic;
    }

    public void setNumpic(String numpic) {
        this.numpic = numpic;
    }

    public String getAllpic() {
        return allpic;
    }

    public void setAllpic(String allpic) {
        this.allpic = allpic;
    }

    public String getBadpic() {
        return badpic;
    }

    public void setBadpic(String badpic) {
        this.badpic = badpic;
    }

    public String getLinelevel() {
        return linelevel;
    }

    public void setLinelevel(String linelevel) {
        this.linelevel = linelevel;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

}
