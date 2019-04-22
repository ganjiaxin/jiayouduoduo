package com.hyk.code.modules.hyk.entity;


public class Resp {

	private int code;

	private String msg = "success";

	private Object data;

	private int totalPage;

	public Resp() {

	}

	public Resp(int code) {
		this.code = code;
	}

	public Resp(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public Resp(Object data) {
		this.data = data;
	}
	
	public Resp(Object data, int totalPage) {
		this.data = data;
		this.totalPage = totalPage;
	}

	public Resp(int code, String msg, Object data) {
		super();
		this.code = code;
		this.msg = msg;
		this.data = data;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}


}
