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
public class HykAd extends DataEntity<HykAd> {

    private static final long serialVersionUID = 1L;
    private String title;        // 活动名称
    private String bannerImg;        // banner缩略图
    private String openImg;        // 开屏广告也缩图
    private String wondefulImg;        // 精彩活动缩略图
    private String appImg;        // App弹窗缩略图
    private String shareImg;        // 分享图片缩略图
    private Date startTime;        // 开始时间
    private Date endTime;        // 结束时间
    private String status;        // 状态
    private String remark;        // 备注
    private String shareTitle;        // 分享主标题
    private String shareSonTitle;        // 分享副标题
    private String shareUrl;//分享地址
    private String url;        // 图片地址
    //================================
    private String startTimeStr;        // 开始时间
    private String endTimeStr;        // 结束时间
    private String overSign;            //结束标记  1已结束  0未结束
    private String statusStr;
    private String sort;//排序字段
    private String goodsImg;//商城图片

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getStatusStr() {
        return statusStr;
    }
    public void setStatusStr(String statusStr) {
        this.statusStr = statusStr;
    }

    public String getStartTimeStr() {
        return startTimeStr;
    }

    public void setStartTimeStr(String startTimeStr) {
        this.startTimeStr = startTimeStr;
    }

    public String getEndTimeStr() {
        return endTimeStr;
    }

    public void setEndTimeStr(String endTimeStr) {
        this.endTimeStr = endTimeStr;
    }

    public HykAd() {
        super();
    }

    public HykAd(String id) {
        super(id);
    }

    @Length(min = 1, max = 100, message = "活动名称长度必须介于 1 和 100 之间")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Length(min = 0, max = 100, message = "banner缩略图长度必须介于 0 和 100 之间")
    public String getBannerImg() {
        return bannerImg;
    }

    public void setBannerImg(String bannerImg) {
        this.bannerImg = bannerImg;
    }

    @Length(min = 0, max = 100, message = "开屏广告也缩图长度必须介于 0 和 100 之间")
    public String getOpenImg() {
        return openImg;
    }

    public void setOpenImg(String openImg) {
        this.openImg = openImg;
    }

    @Length(min = 0, max = 100, message = "精彩活动缩略图长度必须介于 0 和 100 之间")
    public String getWondefulImg() {
        return wondefulImg;
    }

    public void setWondefulImg(String wondefulImg) {
        this.wondefulImg = wondefulImg;
    }

    @Length(min = 0, max = 100, message = "App弹窗缩略图长度必须介于 0 和 100 之间")
    public String getAppImg() {
        return appImg;
    }

    public void setAppImg(String appImg) {
        this.appImg = appImg;
    }

    @Length(min = 0, max = 100, message = "分享图片缩略图长度必须介于 0 和 100 之间")
    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
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

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareSonTitle() {
        return shareSonTitle;
    }

    public void setShareSonTitle(String shareSonTitle) {
        this.shareSonTitle = shareSonTitle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getOverSign() {
        return overSign;
    }

    public void setOverSign(String overSign) {
        this.overSign = overSign;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }
}