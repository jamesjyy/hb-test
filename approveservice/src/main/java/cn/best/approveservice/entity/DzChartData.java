package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class DzChartData implements Serializable {
    private static final long serialVersionUID = -2804524175166688919L;
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
     * 返回的片值总数
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


    /**
     * 片号
     */
    private String slices;

    /**
     * 每一串的总片数
     */
    private String datanum;

    /**
     * 每串上的故障数量
     */
    private String dataling;

}
