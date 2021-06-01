package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class DzDataSup implements Serializable {

    private static final long serialVersionUID = 8650378920114014566L;
    //用户id
    private Integer userId;

    //公司名称
    private String companyname;

    //线路数量
    private Integer linenumber;

    //异常数量
    private Integer errnumber;

    //线路名称
    private List<String> loopidname;
}
