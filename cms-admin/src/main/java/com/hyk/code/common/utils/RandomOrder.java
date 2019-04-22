package com.hyk.code.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/** 
* @author  wyw  
* @Description: TODO(获取订单号) 
* @2016-8-26下午4:24:43
*/ 
public class RandomOrder {
	
	public static synchronized String getRandom(String userId){
		try{
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	      String	  res = "";
		     for(int i = 0; i < 32 ; i ++) {
		         int id = (int) Math.ceil(Math.random()*61);
		         res += chars[id];
		     }
			String outTradeN=userId+System.currentTimeMillis()+""+res;//订单号
			outTradeN=outTradeN.substring(0, 32);
		
		return outTradeN;
	}
	public static synchronized String getContract(){
		try{
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  char[] chars = {'0','1','2','3','4','5','6','7','8','9'};
	      String	  res = "";
		     for(int i = 0; i < 3 ; i ++) {
		         int id = (int) Math.ceil(Math.random()*9);
		         res += chars[id];
		     }
		     
		Date now=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSS");
		String str=sdf.format(now);
		return str+res;
	}
	
	public static synchronized String getRandomNum(String userId){
		try{
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  char[] chars = {'0','1','2','3','4','5','6','7','8','9'};
	      String	  res = "";
		     for(int i = 0; i < 32 ; i ++) {
		         int id = (int) Math.ceil(Math.random()*9);
		         res += chars[id];
		     }
			String outTradeN=userId+System.currentTimeMillis()+""+res;//订单号
			outTradeN=outTradeN.substring(0, 32);
		
		return outTradeN;
	}
	
	public static synchronized String getYanzm(int num){
		try{
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  char[] chars = {'0','1','2','3','4','5','6','7','8','9'};
	      String	  res = "";
		     for(int i = 0; i < num ; i ++) {
		         int id = (int) Math.ceil(Math.random()*9);
		         res += chars[id];
		     }
			String outTradeN=res;//验证码
		return outTradeN;
	}
	
	public static synchronized String getRandom(){
		try{
			Thread.sleep(1L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		  char[] chars = {'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};
	      String	  res = "";
		     for(int i = 0; i < 32 ; i ++) {
		         int id = (int) Math.ceil(Math.random()*61);
		         res += chars[id];
		     }
			String outTradeN=System.currentTimeMillis()+""+res;
			outTradeN=outTradeN.substring(0, 43);
		
		return outTradeN;
	}
	public static Integer getRandomReward(){
			List<Integer> list=new ArrayList<Integer>();
		     for(int i = 0; i < 45 ; i ++) {
		        list.add(6);
		     }
		     list.add(1);
		     list.add(2);
		     list.add(3);
		     list.add(4);
		     list.add(5);
		     list.add(6);
		return list.get((int) Math.ceil(Math.random()*49));//此次没有0 所以第一个值取不到 概率20%
	}
	public static void main(String args[]){
		RandomOrder random=new RandomOrder();
		System.out.println(random.getContract());
	}
}
