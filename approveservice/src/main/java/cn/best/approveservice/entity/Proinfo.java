package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (Proinfo)实体类
 *
 * @author makejava
 * @since 2021-04-08 10:17:57
 */
public class Proinfo implements Serializable {
    private static final long serialVersionUID = 943580826142957365L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 版本号
     */
    private String version;

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getBeiyong2() {
        return beiyong2;
    }

    public void setBeiyong2(String beiyong2) {
        this.beiyong2 = beiyong2;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    /**
     * 产品串号
     */
    private String appid;
    /**
     * 使用人/公司名称
     */
    private String uname;

    private String equipment;

    private  String beiyong2;

    private String count;

    private Integer userid;

    private String createtime;

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    private String updatetime;

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }



    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

}
