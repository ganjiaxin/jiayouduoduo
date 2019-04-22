package com.hyk.code.common.utils;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Auther: 霍中曦
 * @Date: 2018/12/27 13:52
 * @Description:
 */
public class HttpClientDownloadImg {

    public static void downloadImg(String filePath, HttpServletResponse response,String path) {
        OutputStream os=null;
        InputStream is = null;
        try {
            // 下载
            byte[] fileByte =uploadImg(filePath,path);
            if (response != null) {
                os = response.getOutputStream();
            }
            is = new ByteArrayInputStream(fileByte);
            byte[] buffer = new byte[1024 * 5];
            int len = 0;
            while ((len = is.read(buffer)) > 0) {
                os.write(buffer, 0, len);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            // 关闭流
            try {
                if(is != null){
                    is.close();
                }
                if(os != null){
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public static  byte[] uploadImg(String filePath,String path){
        try {
            String str[]=filePath.split("upload");
            String file="/error.jpg";
            if(str.length==2){
                file=str[1];
            }

            //1,导包
            //2,得到HttpClient对象
            HttpClient client = new DefaultHttpClient();

            System.out.println(path+file);
            //3,设置请求方式
            HttpGet get = new HttpGet(path+file);

            //4,执行请求, 获取响应信息
            HttpResponse response = client.execute(get);

            if (response.getStatusLine().getStatusCode() == 200) {
                //得到实体
                HttpEntity entity = response.getEntity();
                byte[] data = EntityUtils.toByteArray(entity);
                System.out.println("图片下载成功!!!!");
                return data;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String args[]){

    }

}
