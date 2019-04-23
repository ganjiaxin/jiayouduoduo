package com.hyk.code.common.utils;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.aliyun.oss.OSSClient;


public class ImageUploadUtil {

    public static String accessKeyId = "LTAIvx777koCc1Gl";
    public static String accessKeySecret = "kmHQc9d6PoVo87hEFS9AVSbD9OP8zw";
    private static String ossSchema = "http";
    public static String ossEndpoint = "oss-cn-hangzhou.aliyuncs.com";
    public static String ossBucketName = "jiayouduoduo";

    // 图片类型
    private static List<String> fileTypes = new ArrayList<String>();

    static {
        fileTypes.add(".jpg");
        fileTypes.add(".jpeg");
        fileTypes.add(".bmp");
        fileTypes.add(".gif");
        fileTypes.add(".png");
    }

    private static PropertiesLoader loader = new PropertiesLoader("application.properties");
    private static String projectName = loader.getProperty("projectName");

//    /**
//     * 图片上传
//     *
//     * @Title upload
//     * @param request
//     * 文件上传目录：比如upload(无需带前面的/) upload/news ..
//     * @return
//     * @throws IllegalStateException
//     * @throws IOException
//     */
//    public static String upload(HttpServletRequest request) throws IllegalStateException,IOException {
//        // 创建一个通用的多部分解析器
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession()
// .getServletContext());
//        // 图片名称
//        String fileName = null;
//        String path1=null;
//        // 判断 request 是否有文件上传,即多部分请求
//        if (multipartResolver.isMultipart(request)) {
//            // 转换成多部分request
//            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
//            // 取得request中的所有文件名
//            Iterator<String> iter = multiRequest.getFileNames();
//            while (iter.hasNext()) {
//                // 记录上传过程起始时的时间，用来计算上传时间
//                // int pre = (int) System.currentTimeMillis();
//                // 取得上传文件
//                MultipartFile file = multiRequest.getFile(iter.next());
//                if (file != null) {
//                    // 取得当前上传文件的文件名称
//                    String myFileName = file.getOriginalFilename();
//                    // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
//                    if (myFileName.trim() != "") {
//                        // 获得图片的原始名称
//                        String originalFilename = file.getOriginalFilename();
//                        // 获得图片后缀名称,如果后缀不为图片格式，则不上传
//                        String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
//                        if (!fileTypes.contains(suffix)) {
//                            continue;
//                        }
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
//                        fileName = sdf.format(new Date())+suffix ;
//
//                        path1="/upload/"+fileName;
//
//                       //文件保存的图片路径
//                        String filePath = request.getSession().getServletContext().getRealPath("/") + "upload/"+
// fileName;
//                        // 如果路径不存在，则创建该路径
//                        File realPathDirectory = new File(filePath);
//                        if (realPathDirectory == null || !realPathDirectory.exists()) {
//                            realPathDirectory.mkdirs();
//                        }
//                        file.transferTo(realPathDirectory);
//                    }
//                }
//            }
//        }
//        System.out.println("==upload=="+path1);
//        return path1;
//    }


    /**
     * //     * 图片上传
     * //     *
     * //     * @Title upload
     * //     * @param request
     * //     * 文件上传目录：比如upload(无需带前面的/) upload/news ..
     * //     * @return
     * //     * @throws IllegalStateException
     * //     * @throws IOException
     * //
     */
    public static String upload(HttpServletRequest request) throws IllegalStateException, IOException {
        // 创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver =
                new CommonsMultipartResolver(request.getSession().getServletContext());
        // 图片名称
        String fileName = null;
        String path = null;
        // 判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            // 转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            // 取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            while (iter.hasNext()) {
                // 取得上传文件
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    // 获得图片的原始名称
                    String originalFilename = file.getOriginalFilename();
                    String suffix = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    OSSClient ossClient = new OSSClient(ossEndpoint, accessKeyId,
                            accessKeySecret);
                    try {
                        fileName = sdf.format(new Date()) + suffix;
                        String key = "upload/" + fileName;
                        System.out.println(key);
                        ossClient.putObject(ossBucketName, key, file.getInputStream());
                        path = toUrl(key);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        ossClient.shutdown();
                    }

                }
            }
        }
        System.out.println("==upload==" + path);
        return path;
    }

    /**
     * ckeditor文件上传功能，回调，传回图片路径，实现预览效果。
     *
     * @param request
     * @param response 文件上传目录：比如upload(无需带前面的/) upload/..
     * @throws IOException
     * @Title ckeditor
     */
    public static void ckeditor(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String fileName = upload(request);
        // 结合ckeditor功能
        // imageContextPath为图片在服务器地址，如upload/123.jpg,非绝对路径
        String imageContextPath = "/" + projectName + "/" + "upload/" + "ckEditor/" + fileName;
        response.setContentType("text/html;charset=UTF-8");
        String callback = request.getParameter("CKEditorFuncNum");
        PrintWriter out = response.getWriter();
        out.println("<script type=\"text/javascript\">");
        out.println("window.parent.CKEDITOR.tools.callFunction(" + callback + ",'" + imageContextPath + "',''" + ")");
        out.println("</script>");
        out.flush();
        out.close();
    }


    public static void getHttpClientImg() {

    }


    public static String toUrl(String key) {
        return String.format("%s://%s.%s/%s", ossSchema, ossBucketName, ossEndpoint, key);
    }
}
