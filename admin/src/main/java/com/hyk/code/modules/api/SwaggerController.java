package com.hyk.code.modules.api;

import com.hyk.code.modules.hyk.entity.*;
import com.hyk.code.modules.hyk.entity.vo.CompanyMarVo;
import com.hyk.code.modules.hyk.service.HykNoticeService;
import com.hyk.code.modules.sys.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 *  .--,       .--,
 * ( (  \.---./  ) )
 *  '.__/o   o\__.'
 *     {=  ^  =}
 *      >  -  <
 *     /       \
 *    //       \\
 *   //|   .   |\\
 *   "'\       /'"_.-~^`'-.
 *      \  _  /--'         `
 *    ___)( )(___
 *   (((__) (__)))    高山仰止,景行行止.虽不能至,心向往之。
 */

@Api(description = "Swagger例子")
@Validated
@Controller
@RequestMapping(value = "/swagger/")
public class SwaggerController {

    @Autowired
    private DictService dictService;
    @Autowired
    private HykNoticeService hykNoticeService;

    @RequestMapping(value = "index")
    @ApiOperation(httpMethod = "GET", value = "Swagger接口首页")
    public String index( HttpServletRequest request) {
        return "/swagger/index";
    }

    @RequestMapping(value = "test")
    @ApiOperation(httpMethod = "GET", value = "显示models")
    public void test(@RequestBody HykNews hykNews,@RequestBody CompanyMarVo companyMarVo, @RequestBody HykAppVersion hykAppVersion, @RequestBody HykUser hykUser, @RequestBody HykRechargeCard hykRechargeCard, @RequestBody HykCardHis hykCardHis, @RequestBody HykAdvice hykAdvice, @RequestBody HykOrder hykOrder, @RequestBody HykMallGoods hykMallGoods, @RequestBody HykMallOrder hykMallOrder, @RequestBody HykAd hykAd){
    }
}
