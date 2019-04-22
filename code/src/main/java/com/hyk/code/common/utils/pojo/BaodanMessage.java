package com.hyk.code.common.utils.pojo;

public class BaodanMessage {
	
/*	{{first.DATA}}
	投保人：{{keyword1.DATA}}
	保单号：{{keyword2.DATA}}
	{{remark.DATA}}
	尊敬的张三，您的保单已完成打印下发，请及时领取，谢谢！
	投保人：张三
	保单号：20141234567890
	如有问题，请在微信上留言，谢谢！*/
	
	private String first;
	private String keyword1;
	private String keyword2;
	private String remark;
	private String tourl;
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	public String getKeyword1() {
		return keyword1;
	}
	public void setKeyword1(String keyword1) {
		this.keyword1 = keyword1;
	}
	public String getKeyword2() {
		return keyword2;
	}
	public void setKeyword2(String keyword2) {
		this.keyword2 = keyword2;
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
