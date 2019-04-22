package com.hyk.code.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
 
/** 
* 获取经纬度
* 
* @author jueyue 返回格式：Map<String,Object> map map.put("status", 
* reader.nextString());//状态 map.put("result", list);//查询结果 
* list<map<String,String>> 
* 密钥:f247cdb592eb43ebac6ccd27f796e2d2 
*/ 
public class BdUtil { 
     
    /** 
    * @param addr 
    * 查询的地址 
    * @return 
    * @throws IOException 
    */ 
    public  static Object[] getCoordinate(String addr) throws IOException { 
        String lng = null;//经度
        String lat = null;//纬度
        String address = null; 
        try { 
            address = java.net.URLEncoder.encode(addr, "UTF-8"); 
        }catch (UnsupportedEncodingException e1) { 
            e1.printStackTrace(); 
        } 
        String key = "f247cdb592eb43ebac6ccd27f796e2d2"; 
        String url = String .format("http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s", address, key); 
        URL myURL = null; 
        URLConnection httpsConn = null; 
        try { 
            myURL = new URL(url); 
        } catch (MalformedURLException e) { 
            e.printStackTrace(); 
        } 
        InputStreamReader insr = null;
        BufferedReader br = null;
        try { 
            httpsConn = (URLConnection) myURL.openConnection();// 不使用代理 
            if (httpsConn != null) { 
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8"); 
                br = new BufferedReader(insr); 
                String data = null; 
                int count = 1;
                while((data= br.readLine())!=null){ 
                    if(count==5){
                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));//经度
                        count++;
                    }else if(count==6){
                        lat = data.substring(data.indexOf(":")+1);//纬度
                        count++;
                    }else{
                        count++;
                    }
                } 
            } 
        } catch (IOException e) { 
            e.printStackTrace(); 
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        return new Object[]{lng,lat}; 
    } 
    
    /**
     * 经纬度两点之间距离计算
     */
    private static final double EARTH_RADIUS = 6378137;
        private static double rad(double d)
        {
           return d * Math.PI / 180.0;
       }
   
    public static double GetDistance(double lng1, double lat1, double lng2, double lat2){
    	double radLat1 = rad(lat1);
    	double radLat2 = rad(lat2);
    	      double a = radLat1 - radLat2;
    	       double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) + 
    	       Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
          s = s * EARTH_RADIUS;
          s = Math.round(s * 10000) / 10000;
          return s;
    }
 
      public static String getAdd(String log, String lat ){ 
        //lat 小 log 大 
        //参数解释: 纬度,经度 type 001 (100代表道路，010代表POI，001代表门址，111可以同时显示前三项) 
        String urlString = "http://gc.ditu.aliyun.com/regeocoding?l="+lat+","+log+"&type=010"; 
        String res = "";   
        try {   
          URL url = new URL(urlString);  
          java.net.HttpURLConnection conn = (java.net.HttpURLConnection)url.openConnection();  
          conn.setDoOutput(true);  
          conn.setRequestMethod("POST");  
          BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"UTF-8"));
          String line;  
          while ((line = in.readLine()) != null) {  
            res += line+"\n";  
         }  
          in.close();  
        } catch (Exception e) {  
          System.out.println("error in wapaction,and e is " + e.getMessage());  
        }  
        System.out.println(res); 
        return res;  
      } 

     public static Map<String,String> getAddress(String lat,String lng){
    	 //String add = getAdd("117.264786", "31.745564"); 
    	 String add=getAdd(lng,lat);
         JSONObject jsonObject = JSONObject.fromObject(add); 
         JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList")); 
         JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0)); 
         String allAdd = j_2.getString("admName"); 
         String addr= j_2.getString("addr"); 
         String arr[] = allAdd.split(","); 
         for(int i=0;i<arr.length;i++) {
       	  System.out.println("=============="+arr[i]);
         }
         System.out.println("省："+arr[0]+"\n市："+arr[1]+"\n区："+arr[2]+"\n详细："+addr); 
         Map<String,String> map=new HashMap<String,String>();
         map.put("province", arr[0]);
         map.put("city", arr[1]);
         map.put("area", arr[2]);
         map.put("detail", addr);
         return map;
     }
 
    public static void main(String[] args) throws IOException {
/*        BdUtil getLatAndLngByBaidu = new BdUtil();
        Object[] o = getLatAndLngByBaidu.getCoordinate("合肥市荣城花园北苑");
        System.out.println(o[0]);//经度
        System.out.println(o[1]);//纬度
        double a = 31.924595;
        double b = 117.270294;
        double c = GetDistance(b,a,Double.parseDouble(o[0].toString()) ,Double.parseDouble(o[1].toString()));
        System.out.println(c);*/
    	
    	//{"queryLocation":[31.745564,117.264786],"addrList":[{"type":"poi","status":1,"name":"基督教堂(南门)","id":"ANB022715L5T","admCode":"340104","admName":"安徽省,合肥市,蜀山区,","addr":"紫蓬路6号","nearestPoint":[117.26383,31.74745],"distance":211.533}]}

    	  String add = getAdd("117.264786", "31.745564"); 
          JSONObject jsonObject = JSONObject.fromObject(add); 
          JSONArray jsonArray = JSONArray.fromObject(jsonObject.getString("addrList")); 
          JSONObject j_2 = JSONObject.fromObject(jsonArray.get(0)); 
          String allAdd = j_2.getString("admName"); 
          String addr= j_2.getString("addr"); 
          String arr[] = allAdd.split(","); 
          for(int i=0;i<arr.length;i++) {
        	  System.out.println("=============="+arr[i]);
          }
          System.out.println("省："+arr[0]+"\n市："+arr[1]+"\n区："+arr[2]+"\n详细："+addr); 
    }
    
    
    
 
}