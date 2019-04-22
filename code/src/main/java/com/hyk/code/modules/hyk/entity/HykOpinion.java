/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 意见反馈管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykOpinion extends DataEntity<HykOpinion> {

    private static final long serialVersionUID = 1L;
    private String userId;        // 用户编号
    private String opinion;        // 意见反馈
    private String times;        // 发布时间
    private String img;         //图片
    private String status;        // 状态
    private String remark;        // 备注
    private String f1;        // f1
    private String f2;        // f2
    private String f3;        // f3

    public HykOpinion() {
        super();
    }

    public HykOpinion(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public String getTimes() {
        return times;
    }

    public void setTimes(String times) {
        this.times = times;
    }


    @Length(min = 0, max = 2, message = "状态长度必须介于 0 和 2 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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

}