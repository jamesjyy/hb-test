package cn.best.approveservice.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class JfTEVSupData implements Serializable {

    private static final long serialVersionUID = -6278689407718896336L;
    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 站点名称
     */
    private String stationNumber;
}
