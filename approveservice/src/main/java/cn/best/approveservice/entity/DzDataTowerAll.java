package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DzDataTowerAll implements Serializable {


    private static final long serialVersionUID = -4148068687574923862L;
    //相别,一个相别对应多个串号 ,根据ABC进行排序
    private String Phase;

    //串号,存入一个对象,判断如果象限相同就取出来对应的值,根据12345来进行排序
    private List<String> bunch;


}
