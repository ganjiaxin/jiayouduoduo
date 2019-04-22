/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 用户红包管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykOilPrice extends DataEntity<HykOilPrice> {

    private static final long serialVersionUID = 1L;
    private String city;        //省份
    private String b90;         ///*89(90)号*/
    private String b93;         ///*92(93)号*/
    private String b97;         ///*95(97)号*/
    private String b0;          ///*0号*/
    private String h92;         ///*建议取值*/
    private String h95;         ///*建议取值*/
    private String h98;         ///*建议取值*/
    private String h0;          ///*建议取值*/
    private Date createDate;
    private String createDateStr;


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getB90() {
        return b90;
    }

    public void setB90(String b90) {
        this.b90 = b90;
    }

    public String getB93() {
        return b93;
    }

    public void setB93(String b93) {
        this.b93 = b93;
    }

    public String getB97() {
        return b97;
    }

    public void setB97(String b97) {
        this.b97 = b97;
    }

    public String getB0() {
        return b0;
    }

    public void setB0(String b0) {
        this.b0 = b0;
    }

    public String getH92() {
        return h92;
    }

    public void setH92(String h92) {
        this.h92 = h92;
    }

    public String getH95() {
        return h95;
    }

    public void setH95(String h95) {
        this.h95 = h95;
    }

    public String getH98() {
        return h98;
    }

    public void setH98(String h98) {
        this.h98 = h98;
    }

    public String getH0() {
        return h0;
    }

    public void setH0(String h0) {
        this.h0 = h0;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}
