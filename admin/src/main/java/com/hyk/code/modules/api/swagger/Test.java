package com.hyk.code.modules.api.swagger;

import java.text.SimpleDateFormat;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/24 14:54
 * @Description:
 */
public class Test {


    public static void main(String args[]){
        SimpleDateFormat sdf=new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        try {
            System.out.println(sdf.parse("2018-12-31 23:59:59"));
        }catch (Exception e){

        }
    }
}
