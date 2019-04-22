package com.hyk.code.common;
import java.util.Date;

/**
 * 用户操作记录
 * Created by chzhxiang on 2018/4/14.
 */
public class UserOperateLog {

    /** 用户类型 1前台用户 2后台用户 */
    private Integer userType;
    /**操作类型*/
    private Integer operateType;
    /**操作时间*/
    private Date operateTime;
    /**操作IP*/
    private String operateIp;
    /**操作用户*/
    private Long operateUserId;
    /**操作模块*/
    private String module;
    /**执行方法*/
    private String method;
    /**执行参数*/
    private String params;
    /**业务描述*/
    private String description;
    /**操作等级*/
    private Integer level;
    /**请求URl*/
    private String url;
    private String username;


    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Integer getOperateType() {
        return operateType;
    }

    public void setOperateType(Integer operateType) {
        this.operateType = operateType;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperateIp() {
        return operateIp;
    }

    public void setOperateIp(String operateIp) {
        this.operateIp = operateIp;
    }

    public Long getOperateUserId() {
        return operateUserId;
    }

    public void setOperateUserId(Long operateUserId) {
        this.operateUserId = operateUserId;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserOperateLog{" +
                "userType=" + userType +
                ", operateType=" + operateType +
                ", operateTime=" + operateTime +
                ", operateIp='" + operateIp + '\'' +
                ", operateUserId=" + operateUserId +
                ", module='" + module + '\'' +
                ", method='" + method + '\'' +
                ", params='" + params + '\'' +
                ", description='" + description + '\'' +
                ", level=" + level +
                ", url='" + url + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}
