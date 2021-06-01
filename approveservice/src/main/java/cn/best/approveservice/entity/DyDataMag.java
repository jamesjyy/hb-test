package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DyDataMag implements Serializable {


    private static final long serialVersionUID = 7230235748452268180L;

    //主键id
    private Integer id;

    //用户id
    private Integer userId;

    //线路名称
    private String loopidtime;

    //杆塔数量
    private Integer towerNumber;

    //杆塔名称
    private String towernum;

    //异常数量
    private Integer errnumber;

}
