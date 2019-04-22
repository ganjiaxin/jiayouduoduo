/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 地址管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykHolidays extends DataEntity<HykHolidays> {

    private static final long serialVersionUID = 1L;
    private Date calendar;        // 节假日日期
    private String type;        // 节假日类型   0工作日 1双休日 2法定假日
    private Date createDate;        // 创建日期

    public HykHolidays() {
        super();
    }

    public HykHolidays(String id) {
        super(id);
    }
    public Date getCalendar() {
        return calendar;
    }

    public void setCalendar(Date calendar) {
        this.calendar = calendar;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

}