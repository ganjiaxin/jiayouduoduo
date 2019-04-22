/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 站内信管理Entity
 *
 * @author 霍中曦
 * @version 2018-12-18
 */
public class HykMessage extends DataEntity<HykMessage> {

    private static final long serialVersionUID = 1L;
    private String title;        // 标题
    private String content;        // 内容
    private String messStatus;        // 状态
    private String phones;        // 发送对象
    private Date createDate; //创建时间
    private String remark;        // 备注
    private String f1;        // f1
    private String type;        // 站内信类型 0系统发送 1手动发送

    // ----------------------------------逻辑字段
    private String createDateStr;//创建时间字符串
    private String messStatusStr;//显示字典类型

    public String getMessStatusStr() {
        return messStatusStr;
    }

    public void setMessStatusStr(String messStatusStr) {
        this.messStatusStr = messStatusStr;
    }

    public HykMessage() {
        super();
    }

    public HykMessage(String id) {
        super(id);
    }

    @Length(min = 1, max = 64, message = "标题长度必须介于 1 和 64 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 1, max =999, message = "内容长度必须介于 1 和 999 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 1, max = 2, message = "状态长度必须介于 1 和 2 之间")
    public String getMessStatus() {
        return messStatus;
    }

    public void setMessStatus(String messStatus) {
        this.messStatus = messStatus;
    }

    @Length(min = 1, max = 100, message = "发送对象长度必须介于 1 和 100 之间")
    public String getPhones() {
        return phones;
    }

    public void setPhones(String phones) {
        this.phones = phones;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Length(min = 0, max = 64, message = "f1长度必须介于 0 和 64 之间")
    public String getF1() {
        return f1;
    }

    public void setF1(String f1) {
        this.f1 = f1;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }

}