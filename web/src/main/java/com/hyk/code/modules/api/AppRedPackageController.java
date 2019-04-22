package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【红包】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppRedPackageController {
    private final static Logger logger = LoggerFactory.getLogger(AppRedPackageController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykRedpackageService hykRedpackageService;

    /**
     * 功能描述: 查询这个人的可用红包
     *
     * @auther: 霍中曦
     * @param:redType 0套餐充值 1即时充值 2无限制
     * @return:
     * @date: 2018/11/10
     * <p>
     * 默认都有2的类型 传过来0或1就好
     */
    @RequestMapping(value = "mine/redPackage")
    @ResponseBody
    public String mineRedPackage(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "redType") String redType,String minAmount,String goodsId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                List list = hykRedpackageService.queryAllCan(userId, redType,minAmount,goodsId);
                map.put("size", list.size());
                if (!StringUtils.isEmpty(minAmount) && !StringUtils.isEmpty(goodsId)) {
                    List listNot = hykRedpackageService.queryAllNotCan(userId, redType, minAmount, goodsId);
                    list.addAll(listNot);
                }
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取个人可用红包信息异常");
            map.put("code", "400");
            map.put("msg", "获取个人可用红包信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 查询这个人的所有红包
     *
     * @auther: 霍中曦
     * @param:currPage  页数
     * @return:count 红包个数
     * @return:list  所有红包集合
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/allRedPackage")
    @ResponseBody
    public String mineAllRedPackage(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "currPage" ,
                                        required = false,defaultValue = "1") Integer currPage,String status) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Integer count = hykRedpackageService.queryAllCount(userId,status);
                List list = hykRedpackageService.queryAll(userId,status, currPage, pageSize);
                map.put("size", count);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取个人所有红包信息异常");
            map.put("code", "400");
            map.put("msg", "获取个人所有红包信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
