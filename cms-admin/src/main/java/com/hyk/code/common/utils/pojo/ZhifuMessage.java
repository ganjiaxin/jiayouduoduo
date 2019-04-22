package com.hyk.code.common.utils.pojo;

/**
* @ClassName: ZhifuMessage
* @Description: TODO(订单支付成功通知pojo)
* @author A18ccms a18ccms_gmail_com
* @date 2017年11月7日 上午12:05:16
*
*/ 
public class ZhifuMessage {

  	/* {{first.DATA}}
		客户姓名：{{Custname.DATA}}
		投保产品：{{ProductName.DATA}}
		支付号：{{PayNo.DATA}}
		{{remark.DATA}} 
		
		尊敬的客户，您的投保信息如下：

		客户姓名：张三
		投保产品：太平洋车险
		支付号：70165432
  	*/ 
	
	private String first;
	private String custname;
	private String productName;
	private String payNo;
	private String remark;
	
	private String tourl;//跳转地址
	
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getCustname() {
		return custname;
	}
	public void setCustname(String custname) {
		this.custname = custname;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPayNo() {
		return payNo;
	}
	public void setPayNo(String payNo) {
		this.payNo = payNo;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getTourl() {
		return tourl;
	}
	public void setTourl(String tourl) {
		this.tourl = tourl;
	}
	
	
  	
}
