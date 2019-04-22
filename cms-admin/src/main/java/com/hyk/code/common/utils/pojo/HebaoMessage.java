package com.hyk.code.common.utils.pojo;

/**
* @ClassName: HebaoMessage
* @Description: TODO(核保信息pojo)
* @author A18ccms a18ccms_gmail_com
* @date 2017年11月7日 上午12:00:25
*/ 
public class HebaoMessage {
  	/** {{first.DATA}}
		产品名称：{{name.DATA}}
		核保结果：{{result.DATA}}
		{{remark.DATA}}
		
		您好，您购买的保险，未能通过公司核保
		
		产品名称：微互助
		核保结果：未通过核保
		若有疑问，请拨打咨询电话400-419-8888**/
	
	private String first;
	private String name;
	private String result;
	private String remark;
	
	private String tourl;//跳转地址
	
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
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
