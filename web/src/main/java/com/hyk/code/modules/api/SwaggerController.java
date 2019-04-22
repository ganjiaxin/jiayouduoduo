package com.hyk.code.modules.api;

import com.hyk.code.modules.sys.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Api(description = "用户操作类")
@Validated
@Controller
@RequestMapping(value = "/swagger/")
public class SwaggerController {

    @Autowired
    private DictService dictService;

    @RequestMapping(value = "index")
    @ApiOperation(httpMethod = "GET", value = "接口信息")
    public String index( HttpServletRequest request) {
        return "/swagger/index";
    }

}
