/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 地址管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykObject extends DataEntity<HykObject> {

    private static final long serialVersionUID = 1L;

    private String str1;        // String
    private String str2;        // String
    private String str3;        // String
    private String str4;        // String
    private String str5;        // String
    private Date date1;        //
    private Date date2;        //
    private Date date3;        //
    private Date date4;        //
    private Date date5;        //
    private Integer integer1;        //
    private Integer integer2;        //
    private Integer integer3;        //
    private Integer integer4;        //
    private Integer integer5;        //
    private BigDecimal bigDecimal1;
    private BigDecimal bigDecimal2;
    private BigDecimal bigDecimal3;
    private BigDecimal bigDecimal4;
    private BigDecimal bigDecimal5;
    private double double1;
    private double double2;
    private double double3;
    private double double4;
    private double double5;



    public HykObject() {
        super();
    }

    public HykObject(String id) {
        super(id);
    }

    @Override
    public String getDelFlag() {
        return delFlag;
    }

    @Override
    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }
    public String getStr1() {
        return str1;
    }

    public void setStr1(String str1) {
        this.str1 = str1;
    }

    public String getStr2() {
        return str2;
    }

    public void setStr2(String str2) {
        this.str2 = str2;
    }

    public String getStr3() {
        return str3;
    }

    public void setStr3(String str3) {
        this.str3 = str3;
    }

    public String getStr4() {
        return str4;
    }

    public void setStr4(String str4) {
        this.str4 = str4;
    }

    public String getStr5() {
        return str5;
    }

    public void setStr5(String str5) {
        this.str5 = str5;
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }

    public Date getDate4() {
        return date4;
    }

    public void setDate4(Date date4) {
        this.date4 = date4;
    }

    public Date getDate5() {
        return date5;
    }

    public void setDate5(Date date5) {
        this.date5 = date5;
    }

    public Integer getInteger1() {
        return integer1;
    }

    public void setInteger1(Integer integer1) {
        this.integer1 = integer1;
    }

    public Integer getInteger2() {
        return integer2;
    }

    public void setInteger2(Integer integer2) {
        this.integer2 = integer2;
    }

    public Integer getInteger3() {
        return integer3;
    }

    public void setInteger3(Integer integer3) {
        this.integer3 = integer3;
    }

    public Integer getInteger4() {
        return integer4;
    }

    public void setInteger4(Integer integer4) {
        this.integer4 = integer4;
    }

    public Integer getInteger5() {
        return integer5;
    }

    public void setInteger5(Integer integer5) {
        this.integer5 = integer5;
    }

    public BigDecimal getBigDecimal1() {
        return bigDecimal1;
    }

    public void setBigDecimal1(BigDecimal bigDecimal1) {
        this.bigDecimal1 = bigDecimal1;
    }

    public BigDecimal getBigDecimal2() {
        return bigDecimal2;
    }

    public void setBigDecimal2(BigDecimal bigDecimal2) {
        this.bigDecimal2 = bigDecimal2;
    }

    public BigDecimal getBigDecimal3() {
        return bigDecimal3;
    }

    public void setBigDecimal3(BigDecimal bigDecimal3) {
        this.bigDecimal3 = bigDecimal3;
    }

    public BigDecimal getBigDecimal4() {
        return bigDecimal4;
    }

    public void setBigDecimal4(BigDecimal bigDecimal4) {
        this.bigDecimal4 = bigDecimal4;
    }

    public BigDecimal getBigDecimal5() {
        return bigDecimal5;
    }

    public void setBigDecimal5(BigDecimal bigDecimal5) {
        this.bigDecimal5 = bigDecimal5;
    }

    public double getDouble1() {
        return double1;
    }

    public void setDouble1(double double1) {
        this.double1 = double1;
    }

    public double getDouble2() {
        return double2;
    }

    public void setDouble2(double double2) {
        this.double2 = double2;
    }

    public double getDouble3() {
        return double3;
    }

    public void setDouble3(double double3) {
        this.double3 = double3;
    }

    public double getDouble4() {
        return double4;
    }

    public void setDouble4(double double4) {
        this.double4 = double4;
    }

    public double getDouble5() {
        return double5;
    }

    public void setDouble5(double double5) {
        this.double5 = double5;
    }



}