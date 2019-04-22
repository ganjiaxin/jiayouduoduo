package com.hyk.code.common.utils.qrcode;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	String ss="/cyb/userfiles/qrcode";
	if(ss.indexOf("cyb")>-1) {
		System.out.println("=====2==="+ss.indexOf("cyb"));
		ss=ss.replace("/cyb", "");
		System.out.println("=====2==="+ss);
	}else {
		System.out.println("=====1==="+ss.indexOf("cyb"));
	}
	}

}
