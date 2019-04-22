package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/18 14:14
 * @Description:
 */
public class ReportIndex extends DataEntity<ReportIndex> {

    /**昨日注册人数 */
    private Integer  peopleNum;
    /**昨日支付总金额 */
    private BigDecimal totalAmt;
    /**昨日订单数量 */
    private Integer orderNum;
    /**昨日加油金额 */
    private BigDecimal oilAmt;

    private Date startTime;

    private Date endTime;


    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public BigDecimal getOilAmt() {
        return oilAmt;
    }

    public void setOilAmt(BigDecimal oilAmt) {
        this.oilAmt = oilAmt;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public BigDecimal getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(BigDecimal totalAmt) {
        this.totalAmt = totalAmt;
    }

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }
}
