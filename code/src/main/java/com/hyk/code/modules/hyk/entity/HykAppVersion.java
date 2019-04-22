/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.hyk.code.common.persistence.DataEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

/**
 * 版本管理Entity
 *
 * @author 霍中曦
 * @version 2018-12-18
 */
@ApiModel(value = "版本管理Entity")
public class HykAppVersion extends DataEntity<HykAppVersion> {

    private static final long serialVersionUID = 1L;
    @ApiModelProperty(required = false, value = "版本号")
    private String code;        // 版本号
    @ApiModelProperty(required = false, value = "更新内容")
    private String content;        // 更新内容
    @ApiModelProperty(required = false, value = "更新渠道")
    private String appTpye;        // 更新app类型
    @ApiModelProperty(required = false, value = "是否提示")
    private String istip;        //是否提示 0 提示 1强制 2不提示
    @ApiModelProperty(required = false, value = "最低兼容版本")
    private String minVersion;        // 最低兼容版本
    private String defaultVersion;        // 是否是默认版本
    private String url;        // 地址
    private Date createDate;        // f1

    private String appTpyeStr;//APP类型

    //------------------------------------------------------------------------
    private String version;//版本号
    private String msg;//更新内容
    private String updateSign;//是否提示

    public String getAppTpyeStr() {
        return appTpyeStr;
    }

    public void setAppTpyeStr(String appTpyeStr) {
        this.appTpyeStr = appTpyeStr;
    }

    public HykAppVersion() {
        super();
    }

    public HykAppVersion(String id) {
        super(id);
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Length(min = 0, max = 200, message = "更新内容长度必须介于 0 和 200 之间")
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Length(min = 0, max = 2, message = "更新渠道长度必须介于 0 和 2 之间")
    public String getAppTpye() {
        return appTpye;
    }

    public void setAppTpye(String appTpye) {
        this.appTpye = appTpye;
    }

    @Length(min = 0, max = 64, message = "版本号长度必须介于 0 和 64 之间")
    public String getIstip() {
        return istip;
    }

    public void setIstip(String istip) {
        this.istip = istip;
    }

    @Length(min = 0, max = 64, message = "最低兼容版本长度必须介于 0 和 64 之间")
    public String getMinVersion() {
        return minVersion;
    }

    public void setMinVersion(String minVersion) {
        this.minVersion = minVersion;
    }

    @Length(min = 0, max = 2, message = "是否是默认版本长度必须介于 0 和 2 之间")
    public String getDefaultVersion() {
        return defaultVersion;
    }

    public void setDefaultVersion(String defaultVersion) {
        this.defaultVersion = defaultVersion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUpdateSign() {
        return updateSign;
    }

    public void setUpdateSign(String updateSign) {
        this.updateSign = updateSign;
    }

}