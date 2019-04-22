/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 新闻管理Entity
 *
 * @author 霍中曦
 * @version 2019-03-07
 */
public class HykNews extends DataEntity<HykNews> {

    private static final long serialVersionUID = 1L;
    private String title;        // 标题
    private String content;        // 内容
    private String ico;        // 缩略图
    private String url;        // 原文地址
    private String status;        // 状态0不显示1显示
    private String addUserId;        // 创建人
    private String updateUserId;        // 修改人
    private String remark;        // 备注

    private Date createDate;
    private Date UpdateDate;
    private String createDateStr;

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return UpdateDate;
    }

    public void setUpdateDate(Date updateDate) {
        UpdateDate = updateDate;
    }

    public HykNews() {
        super();
    }

    public HykNews(String id) {
        super(id);
    }

    @Length(min = 1, max = 64, message = "标题长度必须介于 1 和 64 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 0, max = 100, message = "缩略图长度必须介于 0 和 100 之间")
    public String getIco() {
        return ico;
    }

    public void setIco(String ico) {
        this.ico = ico;
    }

    @Length(min = 0, max = 100, message = "原文地址长度必须介于 0 和 100 之间")
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Length(min = 0, max = 10, message = "状态0不显示1显示长度必须介于 0 和 10 之间")
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Length(min = 0, max = 64, message = "创建人长度必须介于 0 和 64 之间")
    public String getAddUserId() {
        return addUserId;
    }

    public void setAddUserId(String addUserId) {
        this.addUserId = addUserId;
    }

    @Length(min = 0, max = 64, message = "修改人长度必须介于 0 和 64 之间")
    public String getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(String updateUserId) {
        this.updateUserId = updateUserId;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateDateStr() {
        return createDateStr;
    }

    public void setCreateDateStr(String createDateStr) {
        this.createDateStr = createDateStr;
    }


}