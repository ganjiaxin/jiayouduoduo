package com.hyk.code.common.utils.pojo;

public class NewOrderMessage {

/*	{{first.DATA}}
	车主名称：{{keyword1.DATA}}
	车牌号码：{{keyword2.DATA}}
	投保公司：{{keyword3.DATA}}
	保单状态：{{keyword4.DATA}}
	时间：{{keyword5.DATA}}
	{{remark.DATA}}
	
	您有一条新的订单需处理，可点击查看详情。
	车主名称：张三
	车牌号码：粤B88888
	投保公司：人保财险（惠州）
	保单状态：待核保
	时间：2014年11月25日 15
	如已处理，请忽略本信息，若有任何疑问可直接联系本公司微信客服！

	*/
	
	private String first;
	private String keyword1;
	private String keyword2;
	private String keyword3;
	private String keyword4;
	private String keyword5;
	private String remark;
	private String tourl;//跳转地址
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
	public String getKeyword3() {
		return keyword3;
	}
	public void setKeyword3(String keyword3) {
		this.keyword3 = keyword3;
	}
	public String getKeyword4() {
		return keyword4;
	}
	public void setKeyword4(String keyword4) {
		this.keyword4 = keyword4;
	}
	public String getKeyword5() {
		return keyword5;
	}
	public void setKeyword5(String keyword5) {
		this.keyword5 = keyword5;
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

