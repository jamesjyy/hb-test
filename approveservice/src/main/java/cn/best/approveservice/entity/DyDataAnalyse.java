package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 映射dy数据分析
 */
@Data
public class DyDataAnalyse implements Serializable {

    private static final long serialVersionUID = 6670373698685678544L;
    /**
     * 主键id
     */
    private Integer id;

    /**
     * 相别
     */
    private String phase;
    /**
     * 串号
     */
    private Integer strand;

    /**
     * 片号
     */
    private String slice;

    /**
     * 测量值
     */
    private String measured;

    /**
     * 数据值
     */
    private String result;

    /**
     * 状态值
     */
    private String status;


    //TODO根据线路和塔杆号名称在service里面添加数据,然后直接返回给前台就行,前台不用进行电压的判断
    /**
     * 塔杆号
     */
    private String towernum;

    /**
     * 线路名称
     */
    private String loopname;

    /**
     * 检查时间
     */
    private String time;



}




