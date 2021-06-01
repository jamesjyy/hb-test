package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * (JfTevdatainfo)实体类
 *
 * @author makejava
 * @since 2021-04-23 10:36:40
 */
@Data
public class JfTevdatainfo implements Serializable {
    private static final long serialVersionUID = -23730039626681719L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 检测日期
     */
    private String time;
    /**
     * 站点名称
     */
    private String stationname;
    /**
     * 站点编号
     */
    private String stationnumber;
    /**
     * 测点名称
     */
    private String checkstation;
    /**
     * 测点编号
     */
    private String checknumber;
    /**
     * 计数
     */
    private Integer countnumber;
    /**
     * 峰值,,传递幅值的字符串,中间按照","来进行隔开
     */
    private String peakvalue;
    /**
     * 照片
     */
    private String img;
    /**
     * 检测人员
     */
    private String backupone;
    /**
     * 距离
     */
    private String backuptwo;
    /**
     * 温度,,温度的字符串,中间按照","隔开
     */
    private String backupthree;
    /**
     * 湿度
     */
    private String backupfour;
    /**
     * 地域特征
     */
    private String backupfive;
    /**
     * 缺陷程度
     */
    private String backupsix;

    /**
     * 用户Id
     */
    private Integer userid;

}
