package cn.best.approveservice.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * (Upfiles)实体类
 *
 */
public class UpFiles implements Serializable {
    private static final long serialVersionUID = 683243739380132733L;

    private Integer id;

    private String recordcreatetime;

    private String fileext;

    private String filename;

    private String filepath;

    private Integer sizeb;

    private String sizem;

    private String uuid;

    private Integer picwidth;

    private Integer picheight;

    private Integer userid;

    public Integer getIscheck() {
        return ischeck;
    }

    public void setIscheck(Integer ischeck) {
        this.ischeck = ischeck;
    }

    private Integer ischeck;

    //以下非表中字段
    /*用户名*/
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFileext() {
        return fileext;
    }

    public void setFileext(String fileext) {
        this.fileext = fileext;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Integer getSizeb() {
        return sizeb;
    }

    public void setSizeb(Integer sizeb) {
        this.sizeb = sizeb;
    }

    public String getSizem() {
        return sizem;
    }

    public void setSizem(String sizem) {
        this.sizem = sizem;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getPicwidth() {
        return picwidth;
    }

    public void setPicwidth(Integer picwidth) {
        this.picwidth = picwidth;
    }

    public Integer getPicheight() {
        return picheight;
    }

    public void setPicheight(Integer picheight) {
        this.picheight = picheight;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getRecordcreatetime() {
        return recordcreatetime;
    }

    public void setRecordcreatetime(String recordcreatetime) {
        this.recordcreatetime = recordcreatetime;
    }

    @Override
    public String toString() {
        return "UpFiles{" +
                "id=" + id +
                ", recordcreatetime='" + recordcreatetime + '\'' +
                ", fileext='" + fileext + '\'' +
                ", filename='" + filename + '\'' +
                ", filepath='" + filepath + '\'' +
                ", sizeb=" + sizeb +
                ", sizem='" + sizem + '\'' +
                ", uuid='" + uuid + '\'' +
                ", picwidth=" + picwidth +
                ", picheight=" + picheight +
                ", userid=" + userid +
                ", username='" + username + '\'' +
                '}';
    }
}
