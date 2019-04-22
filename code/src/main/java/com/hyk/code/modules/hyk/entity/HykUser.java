/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hyk.code.common.persistence.DataEntity;
import com.hyk.code.common.utils.excel.annotation.ExcelField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@ApiModel(value = "用户管理")
public class HykUser extends DataEntity<HykUser> {

    private static final long serialVersionUID = 1L;
    private String phone;        // 注册手机号
    private String password;        // 登录密码
    private String inviterId;        // 邀请人id
    private Long payNum;        // 付款交易笔数
    private Long oilCardNum;        // 加油卡数量
    private String channel;        // 渠道
    private BigDecimal waitAmt;        // 待加油金额
    private Long sendCodeError;        // 验证码错误次数
    private Long accountError;        // 账户错误次数
    private String openid;        // 微信唯一标识
    private String realName;        // 真实姓名
    private String headimg;        // 微信头像
    private String sex;        // 性别 1男2女0未知
    private Date birthday;        // 生日
    private Date registerDate;        // 注册时间
    private String remark;        // 备注
    @ApiModelProperty(required = false, value = "所属商户 字典类型:companyId")
    private String companyId;        // 所属商户id
    @ApiModelProperty(required = false, value = "商户类型 0 员工 1商户主 字典类型:isBoss")
    private String isBoss;        // 商户类型 0 员工 1商户主
    @ApiModelProperty(required = false, value = "用户类型 0普通用户 1商户用户 字典类型:isCompany")
    private String isCompany;        //用户类型 0普通用户 1商户用户
    private String qrcode;//个人邀请二维码
    private BigDecimal accumulativeRechargeAmount;        // 累计充值余额
    private BigDecimal accountBalance;        // 剩余余额

    private Date companyAddDate;//员工添加时间

    public Date getCompanyAddDate() {
        return companyAddDate;
    }

    public void setCompanyAddDate(Date companyAddDate) {
        this.companyAddDate = companyAddDate;
    }

    //======================================
    private String registerDateStr2;//日期格式化
    private Long registerDateStr;//日期时间戳
    private Long minPayNum;//最低交易笔数
    private Long maxPayNum;//最高交易笔数

    private String userOther;//多内容查询
    private String inviterOther;//多内容查询

    private String inviterPhone;//邀请人手机号
    @ApiModelProperty(required = false, value = "所属商户 字典名称")
    private String companyName;//所属商户名称
    @ApiModelProperty(required = false, value = "商户类型 字典名称")
    private String isBossStr;//商户类型 0 员工 1商户主
    @ApiModelProperty(required = false, value = "用户类型 字典名称")
    private String isCompanyStr;////用户类型 0普通用户 1商户用户


    private Date registerDateStart;//开始时间
    private Date registerDateEnd;//结束时间

    private BigDecimal amt;//首次实付订单金额
    private String orderType;//首次订单类型
    private String payDate;//首次支付时间
    private String totalAmt;//累计实付金额
    private String idstr;

    @ExcelField(title = "累计实付金额", align = 2, sort = 75)
    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    @ExcelField(title = "用户ID", align = 2, sort = 1)
    public String getIdstr() {
        return idstr;
    }

    public void setIdstr(String idstr) {
        this.idstr = idstr;
    }

    @ExcelField(title = "首次充值金额", align = 2, sort = 50)
    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    @ExcelField(title = "首次充值类型", align = 2, sort = 60)
    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
    @ExcelField(title = "首次充值时间", align = 2, sort = 70)
    public String getPayDate() {
        return payDate;
    }

    public void setPayDate(String payDate) {
        this.payDate = payDate;
    }

    public Date getRegisterDateStart() {
        return registerDateStart;
    }

    public void setRegisterDateStart(Date registerDateStart) {
        this.registerDateStart = registerDateStart;
    }

    public Date getRegisterDateEnd() {
        return registerDateEnd;
    }

    public void setRegisterDateEnd(Date registerDateEnd) {
        this.registerDateEnd = registerDateEnd;
    }

    public String getIsCompanyStr() {
        return isCompanyStr;
    }

    public void setIsCompanyStr(String isCompanyStr) {
        this.isCompanyStr = isCompanyStr;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIsBossStr() {
        return isBossStr;
    }

    public void setIsBossStr(String isBossStr) {
        this.isBossStr = isBossStr;
    }

    public String getIsCompany() {
        return isCompany;
    }

    public void setIsCompany(String isCompany) {
        this.isCompany = isCompany;
    }

    @ExcelField(title = "注册时间", align = 2, sort = 42)
    public String getRegisterDateStr2() {
        return registerDateStr2;
    }

    public void setRegisterDateStr2(String registerDateStr2) {
        this.registerDateStr2 = registerDateStr2;
    }

    public long getRegisterDateStr() {
        return registerDateStr;
    }

    public void setRegisterDateStr(Long registerDateStr) {
        this.registerDateStr = registerDateStr;
    }


    public String getInviterPhone() {
        return inviterPhone;
    }

    public void setInviterPhone(String inviterPhone) {
        this.inviterPhone = inviterPhone;
    }

    public String getUserOther() {
        return userOther;
    }

    public void setUserOther(String userOther) {
        this.userOther = userOther;
    }

    public String getInviterOther() {
        return inviterOther;
    }

    public void setInviterOther(String inviterOther) {
        this.inviterOther = inviterOther;
    }

    public Long getMinPayNum() {
        return minPayNum;
    }

    public void setMinPayNum(Long minPayNum) {
        this.minPayNum = minPayNum;
    }

    public Long getMaxPayNum() {
        return maxPayNum;
    }

    public void setMaxPayNum(Long maxPayNum) {
        this.maxPayNum = maxPayNum;
    }

    public HykUser() {
        super();
    }

    public HykUser(String id) {
        super(id);
    }

    @Length(min = 1, max = 11, message = "注册手机号长度必须介于 1 和 11 之间")
    @ExcelField(title = "注册手机号", align = 2, sort = 10)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Length(min = 1, max = 12, message = "登录密码长度必须介于 1 和 12 之间")
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Length(min = 0, max = 64, message = "邀请人id长度必须介于 0 和 64 之间")
    @ExcelField(title = "邀请人id", align = 2, sort = 20)
    public String getInviterId() {
        return inviterId;
    }

    public void setInviterId(String inviterId) {
        this.inviterId = inviterId;
    }

    @ExcelField(title = "付款交易笔数", align = 2, sort = 40)
    public Long getPayNum() {
        return payNum;
    }

    public void setPayNum(Long payNum) {
        this.payNum = payNum;
    }

    @ExcelField(title = "加油卡数据量", align = 2, sort = 30)
    public Long getOilCardNum() {
        return oilCardNum;
    }

    public void setOilCardNum(Long oilCardNum) {
        this.oilCardNum = oilCardNum;
    }

    @Length(min = 0, max = 10, message = "渠道长度必须介于 0 和 10 之间")
    @ExcelField(title = "渠道", align = 2, sort = 44)
    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    @ExcelField(title = "待加油金额", align = 2, sort = 41)
    public BigDecimal getWaitAmt() {
        return waitAmt;
    }

    public void setWaitAmt(BigDecimal waitAmt) {
        this.waitAmt = waitAmt;
    }

    public Long getSendCodeError() {
        return sendCodeError;
    }

    public void setSendCodeError(Long sendCodeError) {
        this.sendCodeError = sendCodeError;
    }

    public Long getAccountError() {
        return accountError;
    }

    public void setAccountError(Long accountError) {
        this.accountError = accountError;
    }

    @Length(min = 0, max = 64, message = "微信唯一标识长度必须介于 0 和 64 之间")
    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    @Length(min = 0, max = 10, message = "真实姓名长度必须介于 0 和 10 之间")
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    @Length(min = 0, max = 100, message = "微信头像长度必须介于 0 和 100 之间")
    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    @Length(min = 0, max = 2, message = "性别长度必须介于 0 和 2 之间")
    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    @Length(min = 0, max = 100, message = "备注长度必须介于 0 和 100 之间")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Length(min = 0, max = 64, message = "二维码长度必须介于 0 和 64 之间")
    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public BigDecimal getAccumulativeRechargeAmount() {
        return accumulativeRechargeAmount;
    }

    public void setAccumulativeRechargeAmount(BigDecimal accumulativeRechargeAmount) {
        this.accumulativeRechargeAmount = accumulativeRechargeAmount;
    }
    @ExcelField(title = "余额", align = 2, sort = 43)
    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }


    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getIsBoss() {
        return isBoss;
    }

    public void setIsBoss(String isBoss) {
        this.isBoss = isBoss;
    }

    public String getRealInviterId() {
        return realInviterId;
    }

    public void setRealInviterId(String realInviterId) {
        this.realInviterId = realInviterId;
    }

    private String realInviterId;        // queryInfo中真正的邀请码

    @Override
    public String toString() {
        return "HykUser{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", inviterId='" + inviterId + '\'' +
                ", payNum=" + payNum +
                ", oilCardNum=" + oilCardNum +
                ", channel='" + channel + '\'' +
                ", waitAmt=" + waitAmt +
                ", sendCodeError=" + sendCodeError +
                ", accountError=" + accountError +
                ", openid='" + openid + '\'' +
                ", realName='" + realName + '\'' +
                ", headimg='" + headimg + '\'' +
                ", sex='" + sex + '\'' +
                ", birthday=" + birthday +
                ", registerDate=" + registerDate +
                ", remark='" + remark + '\'' +
                ", companyId='" + companyId + '\'' +
                ", isBoss='" + isBoss + '\'' +
                ", isCompany='" + isCompany + '\'' +
                ", qrcode='" + qrcode + '\'' +
                ", accumulativeRechargeAmount=" + accumulativeRechargeAmount +
                ", accountBalance=" + accountBalance +
                ", companyAddDate=" + companyAddDate +
                ", registerDateStr2='" + registerDateStr2 + '\'' +
                ", registerDateStr=" + registerDateStr +
                ", minPayNum=" + minPayNum +
                ", maxPayNum=" + maxPayNum +
                ", userOther='" + userOther + '\'' +
                ", inviterOther='" + inviterOther + '\'' +
                ", inviterPhone='" + inviterPhone + '\'' +
                ", companyName='" + companyName + '\'' +
                ", isBossStr='" + isBossStr + '\'' +
                ", isCompanyStr='" + isCompanyStr + '\'' +
                ", registerDateStart=" + registerDateStart +
                ", registerDateEnd=" + registerDateEnd +
                ", amt=" + amt +
                ", orderType='" + orderType + '\'' +
                ", payDate='" + payDate + '\'' +
                ", realInviterId='" + realInviterId + '\'' +
                '}';
    }
}