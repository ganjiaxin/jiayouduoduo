/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import org.hibernate.validator.constraints.Length;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.hyk.code.common.persistence.DataEntity;

/**
 * 公告管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
public class HykNotice extends DataEntity<HykNotice> {

    private static final long serialVersionUID = 1L;
    private String title;        // 标题
    private Date startTime;        // 开始时间
    private Date endTime;        // 结束时间
    private String status;        // 状态 0隐藏 1 显示
    private String remark;        // 备注
    private String f1;        // f1
    private String f2;        // f2
    private String content;        // 内容
    private String startTimeStr;        // 开始时间
    private String startTimeStrShort;        // 开始时间   不带时分秒
    private String endTimeStr;        // 结束时间
    private String statusStr;

    public String getStatusStr() {
        return statusStr;
    }

    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public HykNotice() {
        super();
    }

    public HykNotice(String id) {
        super(id);
    }

    @Length(min = 0, max = 64, message = "标题长度必须介于 0 和 64 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getEndTime() {
        return endTime;
    }

    public String getStartTimeStrShort() {
        return startTimeStrShort;
    }

    public void setStartTimeStrShort(String startTimeStrShort) {
        this.startTimeStrShort = startTimeStrShort;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

}