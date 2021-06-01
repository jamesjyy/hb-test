package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class DyDataChild implements Serializable {
    private static final long serialVersionUID = -608400156707786749L;

    //用户Id
    private String userId;

    //杆塔名称
    private String towernum;

    //线路名称
    private String loopname;

    //时间,检测一个杆塔的时间,数据库里面的时间应该是相同的
    private String time;

    //相别,一个相别对应多个串号 ,根据ABC进行排序
//    private List<String> Phase;

    //串号,存入一个对象,判断如果象限相同就取出来对应的值,根据12345来进行排序
    private List<DyDataTowerAll> Phase;


}
