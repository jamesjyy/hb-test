package cn.best.approveservice.entity;

import lombok.Data;

@Data
public class TowerList {

    /**
     * 串号
     */
    private String phaseStrand;

    /**
     * 一串的检测结果
     */
    private String result;
}
