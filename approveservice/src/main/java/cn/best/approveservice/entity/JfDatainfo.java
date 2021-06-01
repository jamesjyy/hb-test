package cn.best.approveservice.entity;

import java.io.Serializable;

/**
 * (JfDatainfo)实体类
 *
 * @author makejava
 * @since 2021-04-20 14:02:07
 */
public class JfDatainfo implements Serializable {
    private static final long serialVersionUID = -97320527332914767L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 检测日期
     */
    private String time;
    /**
     * 温度
     */
    private String temperature;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * 编号照片
     */
    private String numberimg;
    /**
     * 整体照片
     */
    private String wholeimg;
    /**
     * 不良照片
     */
    private String badimg;
    /**
     * 音频
     */
    private String audio;
    /**
     * 站点名称
     */
    private String stationname;
    /**
     * 站点编号
     */
    private String stationnumber;
    /**
     * 频率
     */
    private String rcf;
    /**
     * 最大db值
     */
    private String maxdb;
    /**
     * 平均db值
     */
    private String avedb;
    /**
     * 缺陷程度
     */
    private String defect;
    /**
     * 经度
     */
    private String longitude;
    /**
     * 维度
     */
    private String latitude;
    /**
     * 音频
     */
    private String voicepath;
    /**
     * 缺陷程度
     */
    private String defectlevel;
    /**
     * 所属区域
     */
    private String area;
    /**
     * 备用2
     */
    private String backuptwo;
    /**
     * 备用3
     */
    private String backupthree;
    /**
     * 备用4
     */
    private String backupfour;
    /**
     * 备用5
     */
    private String backupfive;
    /**
     * 备用6
     */
    private String backupsix;

    private String deviceHome;
    private String levelDistance;
    private String poleType;
    private String groundResistance;
    private String lineDirection;
    private String testResult;
    private String harmType;
    private String qdata_value;
    private String fdata_value;
    private String xdata_value;
    private String infraredImages;

    public String getInfraredImages() {
        return infraredImages;
    }

    public void setInfraredImages(String infraredImages) {
        this.infraredImages = infraredImages;
    }

    public String getDeviceHome() {
        return deviceHome;
    }

    public void setDeviceHome(String deviceHome) {
        this.deviceHome = deviceHome;
    }

    public String getLevelDistance() {
        return levelDistance;
    }

    public void setLevelDistance(String levelDistance) {
        this.levelDistance = levelDistance;
    }

    public String getPoleType() {
        return poleType;
    }

    public void setPoleType(String poleType) {
        this.poleType = poleType;
    }

    public String getGroundResistance() {
        return groundResistance;
    }

    public void setGroundResistance(String groundResistance) {
        this.groundResistance = groundResistance;
    }

    public String getLineDirection() {
        return lineDirection;
    }

    public void setLineDirection(String lineDirection) {
        this.lineDirection = lineDirection;
    }

    public String getTestResult() {
        return testResult;
    }

    public void setTestResult(String testResult) {
        this.testResult = testResult;
    }

    public String getHarmType() {
        return harmType;
    }

    public void setHarmType(String harmType) {
        this.harmType = harmType;
    }

    public String getQdata_value() {
        return qdata_value;
    }

    public void setQdata_value(String qdata_value) {
        this.qdata_value = qdata_value;
    }

    public String getFdata_value() {
        return fdata_value;
    }

    public void setFdata_value(String fdata_value) {
        this.fdata_value = fdata_value;
    }

    public String getXdata_value() {
        return xdata_value;
    }

    public void setXdata_value(String xdata_value) {
        this.xdata_value = xdata_value;
    }

    public String getTdata_value() {
        return tdata_value;
    }

    public void setTdata_value(String tdata_value) {
        this.tdata_value = tdata_value;
    }

    private String tdata_value;



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getNumberimg() {
        return numberimg;
    }

    public void setNumberimg(String numberimg) {
        this.numberimg = numberimg;
    }

    public String getWholeimg() {
        return wholeimg;
    }

    public void setWholeimg(String wholeimg) {
        this.wholeimg = wholeimg;
    }

    public String getBadimg() {
        return badimg;
    }

    public void setBadimg(String badimg) {
        this.badimg = badimg;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getStationname() {
        return stationname;
    }

    public void setStationname(String stationname) {
        this.stationname = stationname;
    }

    public String getStationnumber() {
        return stationnumber;
    }

    public void setStationnumber(String stationnumber) {
        this.stationnumber = stationnumber;
    }

    public String getRcf() {
        return rcf;
    }

    public void setRcf(String rcf) {
        this.rcf = rcf;
    }

    public String getMaxdb() {
        return maxdb;
    }

    public void setMaxdb(String maxdb) {
        this.maxdb = maxdb;
    }

    public String getAvedb() {
        return avedb;
    }

    public void setAvedb(String avedb) {
        this.avedb = avedb;
    }

    public String getDefect() {
        return defect;
    }

    public void setDefect(String defect) {
        this.defect = defect;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getVoicepath() {
        return voicepath;
    }

    public void setVoicepath(String voicepath) {
        this.voicepath = voicepath;
    }

    public String getDefectlevel() {
        return defectlevel;
    }

    public void setDefectlevel(String defectlevel) {
        this.defectlevel = defectlevel;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getBackuptwo() {
        return backuptwo;
    }

    public void setBackuptwo(String backuptwo) {
        this.backuptwo = backuptwo;
    }

    public String getBackupthree() {
        return backupthree;
    }

    public void setBackupthree(String backupthree) {
        this.backupthree = backupthree;
    }

    public String getBackupfour() {
        return backupfour;
    }

    public void setBackupfour(String backupfour) {
        this.backupfour = backupfour;
    }

    public String getBackupfive() {
        return backupfive;
    }

    public void setBackupfive(String backupfive) {
        this.backupfive = backupfive;
    }

    public String getBackupsix() {
        return backupsix;
    }

    public void setBackupsix(String backupsix) {
        this.backupsix = backupsix;
    }

}
