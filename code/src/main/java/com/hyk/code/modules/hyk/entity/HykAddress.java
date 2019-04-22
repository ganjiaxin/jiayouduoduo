/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Objects;

/**
 * 地址管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykAddress extends DataEntity<HykAddress> {

    private static final long serialVersionUID = 1L;
    private String userId;        // 用户编号
    private String name;        // 收货人
    private String phone;        // 手机号码
    private String address;        // 详细地址
    private String isDefault;        // 是否默认 0其它 1默认
    private String remark;        // 备注
    private String f1;        // f1
    private String f2;        // f2
    private String f3;        // f3

    public HykAddress() {
        super();
    }

    public HykAddress(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 64, message = "f1长度必须介于 0 和 64 之间")
    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    @Length(min = 0, max = 64, message = "f2长度必须介于 0 和 64 之间")
    public String getF2() {
        return f2;
    }

    public void setF2(String f2) {
        this.f2 = f2;
    }

    @Length(min = 0, max = 64, message = "f3长度必须介于 0 和 64 之间")
    public String getF3() {
        return f3;
    }

    public void setF3(String f3) {
        this.f3 = f3;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HykAddress that = (HykAddress) o;
        return Objects.equals(name, that.name) &&
                Objects.equals(phone, that.phone) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phone, address);
    }
}