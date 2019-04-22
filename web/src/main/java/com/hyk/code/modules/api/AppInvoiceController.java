package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.entity.HykInvoice;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykInvoiceService;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【发票】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppInvoiceController {
    private final static Logger logger = LoggerFactory.getLogger(AppInvoiceController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykInvoiceService hykInvoiceService;

    /**
     * 功能描述: 开发票 记录未邮寄发票
     *
     * @auther: 霍中曦
     * @param:redType
     * @return:
     * @date: 2018/11/10
     * <p>
     *
     */
    @RequestMapping(value = "order/generateInvoice")
    @ResponseBody
    public String mineRedPackage(HttpServletRequest request, @RequestParam(value = "token") String token, HykInvoice hykInvoice,@RequestParam(value = "orderIdStr")String orderIdStr) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Integer num=hykInvoiceService.insertInvoiceList(orderIdStr,hykUser,hykInvoice);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("开发票异常");
            map.put("code", "400");
            map.put("msg", "开发票异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }





}
