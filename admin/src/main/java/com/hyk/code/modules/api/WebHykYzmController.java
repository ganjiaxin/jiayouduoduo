package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykYzm;
import com.hyk.code.modules.hyk.service.HykYzmService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2019/2/15 11:33
 * @Description:
 */
@Api(description = "用户管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykYzmController extends BaseController {

    @Autowired
    private HykYzmService hykYzmService;

    @RequestMapping(value = "hykYzm")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "验证码管理")
    public String hykYzm(@RequestParam("callback") String callback, HykYzm hykYzm, HttpServletRequest request, HttpServletResponse response) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code","200");
        map.put("msg","验证码管理请求成功");
        map.put("permission",request.getAttribute("permission"));
        Page<HykYzm> page = hykYzmService.findPage(new Page<HykYzm>(request, response), hykYzm);
        map.put("page", page);
        return  callback+"("+gson.toJson(map)+")";
    }
}
