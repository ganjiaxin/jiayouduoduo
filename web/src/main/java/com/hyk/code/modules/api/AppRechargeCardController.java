package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.dao.HykRechargeCardDao;
import com.hyk.code.modules.hyk.entity.HykRechargeCard;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykCardHisService;
import com.hyk.code.modules.hyk.service.HykRechargeCardService;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import com.hyk.code.modules.hyk.service.HykUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【余额，余额记录】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppRechargeCardController {
    private final static Logger logger = LoggerFactory.getLogger(AppRechargeCardController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykRechargeCardService hykRechargeCardService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykCardHisService hykCardHisService;

    /**
     * 功能描述: 充值余额方法
     *
     * @auther: 霍中曦
     * @param: 卡号 卡密
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "balance/recharge")
    @ResponseBody
    public String balanceRecharge(HttpServletRequest request, @RequestParam(value = "token") String token,
                                  @RequestParam(value = "caredno") String caredno,
                                  @RequestParam(value = "password") String password) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykRechargeCard hykRechargeCard = hykRechargeCardService.getByTwoType(caredno, password);
                if (hykRechargeCard != null) {
                    if (new Date().getTime() < hykRechargeCard.getOverDate().getTime()) {
                        if (hykRechargeCard.getSaleStatus().equals("0")) {
                            Boolean aBoolean = hykRechargeCardService.balanceRechargeFun(hykRechargeCard, hykUser);
                            //执行该执行的方法
                            map.put("money", hykRechargeCard.getMoney());
                            map.put("code", "200");
                            map.put("msg", "余额已成功兑换");
                        } else {
                            map.put("code", "201");
                            map.put("msg", "该卡号已被兑换，请检查后再次兑换");
                        }

                    } else {
                        map.put("code", "202");
                        map.put("msg", "该卡号已过期，如有问题联系客服");
                    }
                } else {
                    map.put("code", "203");
                    map.put("msg", "卡号或密码错误，请检查后兑换");
                }

            }
        } catch (Exception e) {
            logger.info("执行余额充值异常");
            map.put("code", "400");
            map.put("msg", "执行余额充值异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 查看充值记录
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "balance/rechargeRecord")
    @ResponseBody
    public String balanceRechargeRecord(@RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Map map1 = hykCardHisService.getCountMoneyAndCount(userId);
                List list = hykCardHisService.getListByUserId(userId);

                map.put("map", map1);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("查询该用户所有兑换记录异常");
            map.put("code", "400");
            map.put("msg", "查询该用户所有兑换记录异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


//        @RequestMapping(value = "balance/text")
//    @ResponseBody
//    public String balanceRecharge(@RequestParam(value = "id") String caredno) {
//        Gson gson = new Gson();
//        Map map = new HashMap();
//        try {
//
//        } catch (Exception e) {
//            logger.info("执行余额充值异常");
//            map.put("code", "400");
//            map.put("msg", "error");
//            e.printStackTrace();
//        }
//        return gson.toJson(map);
//    }

}
