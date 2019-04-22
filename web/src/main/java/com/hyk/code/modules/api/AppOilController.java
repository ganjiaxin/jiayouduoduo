package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.DateUtils;
import com.hyk.code.common.utils.VliateUser;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykOilCard;
import com.hyk.code.modules.hyk.entity.HykOilManager;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.service.*;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述:App【油卡相关】  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppOilController {
    private final static Logger logger = LoggerFactory.getLogger(AppOilController.class);


    @Autowired
    private HykOilCardService hykOilCardService;
    @Autowired
    private HykOilManagerService hykOilManagerService;
    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykRedpackageService hykRedpackageService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private ActivityService activityService;
    @Autowired
    private HykGoodsService hykGoodsService;


    /**
     * 功能描述: 加油卡接口整合   mine/oilCard   mine/redPackage  details/goods
     *
     * @auther: 霍中曦
     * @param:redType 0套餐充值 1即时充值 2无限制
     * @param:type 类型 1即时充值 0套餐充值
     * @return:list
     * @date: 2018/10/10
     */
    @RequestMapping(value = "oil/integration")
    @ResponseBody
    public String oilIntegration(@RequestParam(value = "token", defaultValue = "null", required = false) String token
            , @RequestParam(value = "redType") String redType,
                                 @RequestParam(value = "type") Integer type, String mineAmount, String goodsId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            if (!token.equals("null")) {
                String userId = TokenUtil.getAppCurrentUser(token).getId();
                if (userId == null || userId.length() <= 0) {
                    map.put("code", "300");
                    map.put("msg", "登录已失效 重新登录");
                } else {
                    List<HykOilCard> oilCardList = hykOilCardService.findById(userId);
                    if (oilCardList.size() == 0) {//用户没有加油卡 按照userId去查加油计划 赋给待加油金额
                        Double userReadyMoney = hykOilManagerService.getMoneyByUserIdAndTime(userId,
                                DateUtils.getYear() + DateUtils.getMonth(), "0");
                        map.put("userReadyMoney", userReadyMoney >= 0 ? userReadyMoney : 0);
                        map.put("userEndMoney", 0);
                    }
                    Integer readyCount = hykOilManagerService.getCountOilManagerByUserId(userId);
                    // System.out.println("本月带加油计划条数-------------" + readyCount);

                    String sign = "0";
                    if (oilCardList.size() == 0 && readyCount > 0) {
                        sign = "1";
                    }
                    map.put("sign", sign);//油卡加油计划标记 只有没有加油卡有加油计划时为1
                    map.put("oilCardSize", oilCardList.size());//加油卡数量
                    map.put("oilCardList", oilCardList);//加油卡集合


//                    List redPackageList = hykRedpackageService.queryAllCan(userId, redType, mineAmount,goodsId);
//                    map.put("redPackageSize", redPackageList.size());//红包数量
//                    map.put("redPackageList", redPackageList);//红包集合


                    Integer count = 0;
                    if (!token.equals("null")) {
                        if (userId != null && userId.length() > 0) {
                            count = hykRedpackageService.queryAllCanCount(userId, type + "");//这里加了一个 个人可用红包个数
                        }
                    }
                    List list1 = hykGoodsService.getByType(type);//即时充值集合
                    map.put("list1", list1);//即时充值集合
                    map.put("count", count);//个人未使用红包总数


                    map.put("code", "200");
                    map.put("msg", "success");
                }
            }
        } catch (Exception e) {
            logger.info("获取加油卡信息异常");
            map.put("code", "400");
            map.put("msg", "获取加油卡信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 添加加油卡
     *
     * @param name    姓名
     * @param oilType 加油卡类型 1中石油16位卡号     2中石化19位卡号
     * @param cardNum 加油卡卡号
     * @param phone   手机号
     * @return
     * @auther: 霍中曦
     * @date: 2018/11/10
     * <p>
     * 先判断token 后将参数添加进hykOilCard对象中执行添加方法 不判断是否有用户绑定了这个加油卡
     */
    @RequestMapping(value = "mine/addOilCard")
    @ResponseBody
    public String mineAddOilCard(@RequestParam(value = "token") String token, @RequestParam(value = "name") String name
            , @RequestParam(value = "oilType") String oilType, @RequestParam(value = "cardNum") String cardNum
            , @RequestParam(value = "phone") String phone) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                if (VliateUser.isPhone(phone)) {
                    if (VliateUser.ValidateAllNum(cardNum)) {
                        if (oilType.equals("1") && cardNum.length() == 16 || oilType.equals("2") && cardNum.length() == 19) {
                            HykOilCard hykOilCard = new HykOilCard();
                            hykOilCard.setId(ZxingHandler.getUUID());
                            hykOilCard.setUserId(userId);
                            hykOilCard.setOliCardNo(cardNum);
                            hykOilCard.setName(name);
                            hykOilCard.setPhone(phone);
                            hykOilCard.setOilType(oilType);
                            Integer num = hykOilCardService.addCard(hykOilCard);

//--------------------------------------------先下单后绑卡做的事情------------------------------------------------------
                            Boolean aBoolean = hykOrderService.orderFun(hykOilCard.getId(), cardNum, userId);
//--------------------------------------------先下单后绑卡做的事情------------------------------------------------------


//------------------------------------------------里面是拉新活动--------------------------------------------------------
                            Integer num1 = activityService.inviteFriendActivityA(userId);
//------------------------------------------------里面是拉新活动--------------------------------------------------------
                            if (num == 1) {
                                try {
                                    hykUserService.updateOilCardNum(1, userId);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                    logger.info("添加加油卡成功，更改user表油卡数量异常");
                                }
                            }

                            map.put("code", "200");
                            map.put("msg", "success");
                        } else {
                            map.put("code", "201");
                            map.put("msg", "加油卡号不合规,长度错误");
                        }
                    } else {
                        map.put("code", "203");
                        map.put("msg", "加油卡号不合规,包含非法字符");
                    }
                } else {
                    map.put("code", "202");
                    map.put("msg", "手机号不合规");
                }
            }
        } catch (Exception e) {
            logger.info("添加加油卡异常");
            map.put("code", "400");
            map.put("msg", "添加加油卡异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 查询这个人的所有加油卡
     *
     * @auther: 霍中曦
     * @param:
     * @return:boolean 显示问题 0 没有加油卡 没有加油计划
     * 1 没有加油卡 有加油计划
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/oilCard")
    @ResponseBody
    public String mineOilCard(HttpServletRequest request, @RequestParam(value = "token", defaultValue = "null",
            required = false) String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            if (!token.equals("null")) {
                String userId = TokenUtil.getAppCurrentUser(token).getId();
                if (userId == null || userId.length() <= 0) {
                    map.put("code", "300");
                    map.put("msg", "登录已失效 重新登录");
                } else {
                    List<HykOilCard> oilCardList = hykOilCardService.findById(userId);
                    if (oilCardList.size() == 0) {//用户没有加油卡 按照userId去查加油计划 赋给待加油金额
                        Double userReadyMoney = hykOilManagerService.getMoneyByUserIdAndTime(userId,
                                DateUtils.getYear() + DateUtils.getMonth(), "0");
                        map.put("userReadyMoney", userReadyMoney >= 0 ? userReadyMoney : 0);
                        map.put("userEndMoney", 0);
                    }
                    Integer readyCount = hykOilManagerService.getCountOilManagerByUserId(userId);
                    // System.out.println("本月带加油计划条数-------------" + readyCount);

                    String sign = "0";
                    if (oilCardList.size() == 0 && readyCount > 0) {
                        sign = "1";
                    }
                    map.put("sign", sign);//是否显示*暂无油卡加油计划无法执行*的标记  1显示0不显示
                    map.put("size", oilCardList.size());
                    map.put("list", oilCardList);
                    map.put("code", "200");
                    map.put("msg", "success");
                }
            } else {
                map.put("size", 0);
                map.put("list", new ArrayList<>());
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取我的加油卡异常");
            map.put("code", "400");
            map.put("msg", "获取我的加油卡异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 删除油卡
     *
     * @auther: 霍中曦
     * @param:id 加油卡id
     * @return:
     * @date: 2018/11/10
     * <p>
     * 先判断是否有未加油的加油计划 未付款订单 有不删除 没有删除
     */
    @RequestMapping(value = "mine/delOilCard")
    @ResponseBody
    public String mineDelOilCard(HttpServletRequest request, @RequestParam(value = "token") String token,
                                 @RequestParam(value = "id") String id) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {


//---------------------------------------------想要在这加一个判断是否还有未付款订单------------------------------------------------------------------------
                Integer oilManagerCount = hykOilManagerService.queryCountByCardId(id);
                if (oilManagerCount > 0) {
                    map.put("code", "201");
                    map.put("msg", "还存在未加油计划，不能删除");
                    return gson.toJson(map);
                }

                Integer readyOrderCount = hykOrderService.queryReadyOrderCount(id);
                if (readyOrderCount > 0) {
                    map.put("code", "202");
                    map.put("msg", "还存在未付款订单，不能删除");
                    return gson.toJson(map);
                }


                Integer num = hykOilCardService.softDeleteById(id, userId);
                if (num == 1) {
                    try {
                        hykUserService.updateOilCardNum(-1, userId);
                    } catch (Exception e) {
                        e.printStackTrace();
                        logger.info("删除加油卡成功，更改user表油卡数量异常");
                    }
                }
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("删除油卡异常");
            map.put("code", "400");
            map.put("msg", "删除油卡异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 查看该用户所有加油计划
     *
     * @auther: 霍中曦
     * @param:time 格式 201811
     * @return:
     * @date: 2018/11/10
     * <p>
     * 方法返回 已加总额 未加总额  两种加油计划list
     */
    @RequestMapping(value = "mine/oilPlanNew")
    @ResponseBody
    public String mineOilPlanNew(HttpServletRequest request, @RequestParam(value = "token") String token,
                                 @RequestParam(value = "time") String time, @RequestParam(value = "day", required =
            false, defaultValue = "") String day) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Double readyMoney = hykOilManagerService.getMoneyByUserIdAndTimeAndStatus(userId, time, "0");//未加总额
                Double endMoney = hykOilManagerService.getMoneyByUserIdAndTimeAndStatus(userId, time, "1");//已加总额
                List readyList = hykOilManagerService.getAllByUserIdAndTimeAndStatus(userId, time, day, "0");//未加油集合
                List endList = hykOilManagerService.getAllByUserIdAndTimeAndStatus(userId, time, day, "1");//已加油集合
                map.put("readyMoney", readyMoney);
                map.put("endMoney", endMoney);
                map.put("readyList", readyList);
                map.put("endList", endList);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取加油计划信息异常");
            map.put("code", "400");
            map.put("msg", "获取加油计划信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 查看该用户所有加油计划
     *
     * @auther: 霍中曦
     * @param:time 格式 201811
     * @return:
     * @date: 2018/11/10
     * <p>
     * 方法返回 已加总额 未加总额  两种加油计划list
     */
    @RequestMapping(value = "mine/oilPlanCalendar")
    @ResponseBody
    public String mineOilPlanCalendar(HttpServletRequest request, @RequestParam(value = "token") String token,
                                      @RequestParam(value = "time") String time) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Double readyMoney = hykOilManagerService.getMoneyByUserIdAndTimeAndStatus(userId, time, "0");//未加总额
                Double endMoney = hykOilManagerService.getMoneyByUserIdAndTimeAndStatus(userId, time, "1");//已加总额
                List<String> timeList = hykOilManagerService.getTimeGroupByUserId(userId, time);
                List<Map<String, Object>> list = new ArrayList<>();
                for (String timeStr : timeList) {
                    Map map1 = new HashMap();
                    List<HykOilManager> list2 = hykOilManagerService.getAllByUserIdAndTimeAndStatusNew(userId, timeStr);
                    map1.put("date", timeStr);
                    map1.put("list2", list2);
                    list.add(map1);
                }

                map.put("readyMoney", readyMoney);
                map.put("endMoney", endMoney);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取加油计划信息异常");
            map.put("code", "400");
            map.put("msg", "获取加油计划信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 返回当前参数的加油计划 只用来给前端显示
     *
     * @auther: 霍中曦
     * @param:stagesAmt 每期金额
     * @param:cycel 周期
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/oilManager")
    @ResponseBody
    public String mineOilManager(HttpServletRequest request, @RequestParam(value = "cycel") Integer cycel,
                                 @RequestParam(value = "stagesAmt") String stagesAmt) {
        Gson gson = new Gson();
        Map map = new HashMap();
        List list = new ArrayList();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            for (int i = 1; i <= cycel; i++) {
                Map map1 = new HashMap();
                String head = stagesAmt + " (" + i + "/" + cycel + ")";
                String foot = null;
                if (i == 1) {
                    foot = "1个工作日内到账";
                } else {
                    Calendar c = Calendar.getInstance();
                    c.add(Calendar.MONTH, i - 1);
                    foot = sdf.format(c.getTime());
                }
                map1.put("head", head);
                map1.put("foot", foot);
                list.add(map1);
            }
            map.put("hykOrder", list);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("当前参数的加油计划信息异常");
            map.put("code", "400");
            map.put("msg", "当前参数的加油计划信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 查看本月应加油金额 and 已加油金额
     * 未被使用
     *
     * @auther: 霍中曦
     * @param:cardNo 加油卡号
     * @return:
     * @date: 2018/11/10
     */
    // @RequestMapping(value = "first/i")
    @ResponseBody
    public String i(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value =
            "cardNo") String cardNo) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Double readyMoney = hykOilManagerService.getMoeyByCardNoAndUserId(userId, cardNo, "0");
                Double endMoney = hykOilManagerService.getMoeyByCardNoAndUserId(userId, cardNo, "1");
                map.put("readyMoney", readyMoney);//未加油金额
                map.put("endMoney", endMoney);//已加油金额
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取本月加油信息异常");
            map.put("code", "400");
            map.put("msg", "获取本月加油信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


//    @RequestMapping(value = "first/text")
//    @ResponseBody
//    public String test(@RequestParam("orderNo") String orderNo) {
//        Gson gson = new Gson();
//
//
//
//        int zzz = hykOilManagerService.insertOilManagerList(orderNo);  //生成加油计划
//        int xxx = hykOrderService.updatePayTypeByOrderNo("！@#￥！", orderNo);// 更改order状态 随便写一个payType   1. 2. 3. 4
//        HykOrder hykOrder = hykOrderService.findByOrderNo(orderNo);
//        if (hykOrder.getRedpackageId() != null) {
//            int ccc = hykRedpackageService.updateUseTime(hykOrder.getRedpackageId());//如果使用了红包 更改红包状态
//        }
//
//
//
//        System.out.println();
//        return gson.toJson(new Date());
//    }


}
