package com.hyk.code.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/28 10:40
 * @Description:
 */
public class WuliuUtils {

/**
 * 功能描述:物流查询
 * @auther: 霍中曦
1	ems	EMS快递
2	shentong	申通快递
3	shunfeng	顺丰快递
4	yuantong	圆通快递
5	yunda	韵达快递
6	huitong	百世汇通快递
7	tiantian	天天快递
8	zhongtong	中通快递
9	zhaijisong	宅急送快递
10	pingyou	中国邮政
12	guotong	国通快递
13	jingdong	京东快递
 */

public static String QueryOrder(String type,String postid){
    String resp="";
    try {
        Map<String, String> queryParas=new HashMap<String,String>();
        String url="http://www.kuaidi100.com/query";
        queryParas.put("type",type);
        queryParas.put("postid",postid);
        resp=HttpKit.get(url,queryParas);
    } catch (Exception e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
    return resp;
}

    public static void main(String[] args) throws Exception {
        //http://www.kuaidi100.com/query?type=shentong&postid=3392097247724
        String mgss=QueryOrder("shentong","3392097247724-0101");
        System.out.println(mgss);
    }
}
