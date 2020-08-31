package com.example.bootjedis.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;

@Data
public class ChannelData implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    private Integer id;

    /**
     * 产品
     */
    private String product;

    /**
     * 渠道
     */
    private String channel;

    /**
     * 推广日期
     */
    private Date popularizeDate;

    /**
     * 推广金额
     */
    private BigDecimal popularizeMoney;

    /**
     * 转化量
     */
    private Long transferation;

    /**
     * 角色类型 (1 唱吧人员  2. 代理商)
     */
    private Integer roleType;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 录入日期
     */
    private Date createDate;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private Date modifyTime;



    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", product=").append(product);
        sb.append(", channel=").append(channel);
        sb.append(", popularizeDate=").append(popularizeDate);
        sb.append(", popularizeMoney=").append(popularizeMoney);
        sb.append(", transferation=").append(transferation);
        sb.append(", createDate=").append(createDate);
        sb.append(", createTime=").append(createTime);
        sb.append(", creator=").append(creator);
        sb.append(", modifier=").append(modifier);
        sb.append(", modifyTime=").append(modifyTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}