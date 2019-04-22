package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.utils.ImageUploadUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/27 16:05
 * @Description:
 */

@Api(description = "用户操作类")
@Validated
@Controller
@RequestMapping(value = "/uploadFile/")
public class UploadFileController {


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String upload(HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            String path=ImageUploadUtil.upload(request);
            map.put("code",200);
            map.put("url",path);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code",400);
        }
        return gson.toJson(map);
    }


/**
 * 功能描述:上传图片Ckedit
 * @auther: 霍中曦
 * @date: 2018/11/27 17:07
 */
    @RequestMapping(value = "/uploadCkEditor", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String uploadCkEditor(HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map = new HashMap<String, Object>();
        try {
            String mHttpUrlName=request.getRequestURI();//项目名称+文件路径
            String mScriptName=request.getServletPath();//文件路径
            String serverName=request.getServerName();//服务地址
            int port=request.getServerPort();//端口
            String mServerUrl="http://"+request.getServerName()+":"+request.getServerPort()+mHttpUrlName.substring(0,mHttpUrlName.lastIndexOf(mScriptName));//取得OfficeServer文件的完整URL

            String path=ImageUploadUtil.upload(request);
            map.put("uploaded",1);
            map.put("url",mServerUrl+path);
        }catch (Exception e){
            e.printStackTrace();
            map.put("uploaded",2);
        }
        return gson.toJson(map);
    }

}
