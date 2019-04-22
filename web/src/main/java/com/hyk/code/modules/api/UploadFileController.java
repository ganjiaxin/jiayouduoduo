package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.utils.ImageUploadUtil;
import com.hyk.code.modules.api.swagger.UserVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.shiro.codec.Base64;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @ApiResponses(
            value = {
                    @ApiResponse(code = 500, message = "系统错误"),
                    @ApiResponse(code = 200, message = "200 成功,其它为错误,返回格式：{code:200,data[{}]},data中的属性参照下方Model", response = UserVo.class)
            })
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String upload(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String path = ImageUploadUtil.upload(request);
            map.put("code", 200);
            map.put("url", path);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 400);
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:上传图片Ckedit
     *
     * @auther: 霍中曦
     * @date: 2018/11/27 17:07
     */
    @RequestMapping(value = "/uploadCkEditor", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String uploadCkEditor(HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String mHttpUrlName = request.getRequestURI();//项目名称+文件路径
            String mScriptName = request.getServletPath();//文件路径
            String serverName = request.getServerName();//服务地址
            int port = request.getServerPort();//端口
            String mServerUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + mHttpUrlName.substring(0, mHttpUrlName.lastIndexOf(mScriptName));//取得OfficeServer文件的完整URL

            String path = ImageUploadUtil.upload(request);
            map.put("uploaded", 1);
            map.put("url", mServerUrl + path);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("uploaded", 2);
        }
        return gson.toJson(map);
    }


    /*
     * 采用file.Transto 来保存上传的文件
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public String fileUpload2(@RequestParam("file") CommonsMultipartFile file) throws IOException {
        long startTime = System.currentTimeMillis();
        System.out.println("fileName：" + file.getOriginalFilename());
        String path = "D:/" + new Date().getTime() + file.getOriginalFilename();

        File newFile = new File(path);
        //通过CommonsMultipartFile的方法直接写文件（注意这个时候）
        file.transferTo(newFile);
        long endTime = System.currentTimeMillis();
        System.out.println("方法二的运行时间：" + String.valueOf(endTime - startTime) + "ms");
        return "/success";
    }


    /**
     * 将Base64位编码的图片进行解码，并保存到指定目录
     *
     * @param base64 base64编码的图片信息
     * @return
     */
    @RequestMapping(value = "/uploadCkEditor3", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String uploadCkEditor3(HttpServletRequest request, HttpServletResponse response, String base64) {
        String mHttpUrlName = request.getRequestURI();//项目名称+文件路径
        String mScriptName = request.getServletPath();//文件路径
        String mServerUrl = "http://" + request.getServerName() + ":" + request.getServerPort() + mHttpUrlName.substring(0, mHttpUrlName.lastIndexOf(mScriptName));
        String imgName = new Date().getTime() + "";
        String path = mServerUrl + "/upload/" + imgName;

        BASE64Decoder decoder = new BASE64Decoder();


        try {
            FileOutputStream write = new FileOutputStream(new File(path));
            byte[] decoderBytes = decoder.decodeBuffer(base64);
            write.write(decoderBytes);
            write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }


    @RequestMapping(value = "/uploadCkEditorm", method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "上传图片")
    public String GenerateImage(@RequestParam("file") String imgStr, @RequestParam("type") String type, HttpServletRequest request) {   //对字节数组字符串进行Base64解码并生成图片
        Gson gson = new Gson();
        Map map = new HashMap();
        if (imgStr == null) //图像数据为空
            return "null";
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //Base64解码
            byte[] b = decoder.decodeBuffer(imgStr.substring(imgStr.indexOf(",") + 1));
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
            String fileName = sdf.format(new Date());
            String path = request.getSession().getServletContext().getRealPath("/") + "upload/" + fileName + "." + type.substring(6);
            String createPath = request.getSession().getServletContext().getRealPath("/") + "upload";
            File file = new File(createPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            String path1 = "/upload/" + fileName + "." + type.substring(6);
            //生成jpeg图片
            String imgFilePath = path;//"D:\\tupian\\new.jpg";//新生成的图片
            OutputStream out = new FileOutputStream(imgFilePath);
            out.write(b);
            out.flush();
            out.close();
            map.put("code", "200");
            map.put("msg", "success");
            map.put("url", path1);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "error");
        }
        return gson.toJson(map);
    }

}
