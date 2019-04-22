package com.hyk.code.modules.sys.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hyk.code.common.utils.ImageUploadUtil;



@Controller
@RequestMapping(value = "${adminPath}/sys/ckEditor")
public class CkEditorImageUpload {

	/**
     * ckeditor图片上传
     * 
     * @Title imageUpload
     * @param request
     * @param response
     */
    @RequestMapping("/imageUpload")
    public void imageUpload(HttpServletRequest request, HttpServletResponse response) {
       // String DirectoryName = "upload/";
        try {
            ImageUploadUtil.ckeditor(request, response);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
