package com.hyk.code.common.utils;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;


public class DateUtil {
    /**
     * @param :请求接口   http://tool.bitefu.net/jiari/vip.php
     * @param httpArg :日期参数yyyyMMdd
     * @return 返回结果
     */
    public JSONObject request(String httpArg) {
        BufferedReader reader = null;
        String result = null;
        JSONObject jsonObjectResult = null;
        StringBuffer sbf = new StringBuffer();
        String httpUrl = "http://api.goseek.cn/Tools/holiday?date=" + httpArg;

        try {
            URL url = new URL(httpUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream is = connection.getInputStream();
            reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            String strRead = null;
            while ((strRead = reader.readLine()) != null) {
                sbf.append(strRead);
                sbf.append("\r\n");
            }
            reader.close();
            result = sbf.toString();
            jsonObjectResult = JSONObject.fromObject(result);//转为JSONObject对象
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonObjectResult;
    }


    public static void main1(String[] args) {
        JSONObject a = new DateUtil().request("20181124");  //0工作日 1双休日 2法定节假日
        System.out.println(a);
    }

    /**
     * 传入给定日期 判断今日是否节假日 是 直接返回是当前日期 不是返回最近的工作日
     *
     * @param date
     * @return
     */
    public static Date workingDay(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String httpArg = sdf.format(date);
        JSONObject a = new DateUtil().request(httpArg);

        Calendar cal= Calendar.getInstance();
        cal.setTime(date);//设置起时间
        String code=a.get("data")+"";
        if (!code.equals(0)) {
            for (int i=1;"1".equals(code)||"2".equals(code);i++) {
                cal.add(Calendar.DATE, 1);//增加一天  

                httpArg = sdf.format(cal.getTime());
                JSONObject b = new DateUtil().request(httpArg);
                code=b.get("data")+"";
                System.out.println(cal);
                System.out.println(code);
            }
            try {
                date = cal.getTime();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        //获取时间加一年或加一月或加一天
//Date date = new Date();
//Calendar cal = Calendar.getInstance();
//cal.setTime(date);//设置起时间
//System.out.println("111111111::::"+cal.getTime());
//cal.add(Calendar.YEAR, 1);//增加一年
//cd.add(Calendar.DATE, n);//增加一天  
//cd.add(Calendar.DATE, -10);//减10天  
//cd.add(Calendar.MONTH, n);//增加一个月   
//System.out.println("输出::"+cal.getTime());


        return date;
    }


    /**
     * 返回code   0工作日 1双休日 2法定假日
     *
     * @param date
     * @return
     */
    public static String holidaysCode(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        String httpArg = sdf.format(date);
        JSONObject a = new DateUtil().request(httpArg);
        String code=a.get("data")+"";

        return code;
    }


    public static void main(String[] args) {
        DateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());//设置起时间
        cal.add(Calendar.DATE,4);//增加一天  


        System.out.println();
        System.out.println(             sdf1.format(        workingDay(cal.getTime())               )          );
    }
}
