package com.hyk.code.modules.api;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.WuliuUtils;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.modules.hyk.dao.HykMallGoodsDao;
import com.hyk.code.modules.hyk.entity.*;
import com.hyk.code.modules.hyk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App商品相关内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppMallGoodsController {
    private final static Logger logger = LoggerFactory.getLogger(AppMallGoodsController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykMallGoodsService hykMallGoodsService;
    @Autowired
    private HykMallOrderService hykMallOrderService;
    @Autowired
    private HykAddressService hykAddressService;
    @Autowired
    private HykAdService hykAdService;


    /**
     * 功能描述: 商城banner
     *
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/banner")
    @ResponseBody
    public String shopBanner(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List list = hykAdService.goodsImg();
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取商城banner异常");
            map.put("code", "400");
            map.put("msg", "获取商城banner异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 所有可展示商品
     *
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/allMallGoods")
    @ResponseBody
    public String shopAllMallGoods(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            //所有可展示商品
            List list = hykMallGoodsService.getCategoryAndCategoryName();
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("获取商城商品信息异常");
            map.put("code", "400");
            map.put("msg", "获取商城商品信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 所有可展示商品
     *
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/allMallGoodsToAndroid")
    @ResponseBody
    public String shopAllMallGoodsToAndroid(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            //所有可展示商品
            List list = hykMallGoodsService.getCategoryAndCategoryNametoAndroid();
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("获取商城商品信息异常");
            map.put("code", "400");
            map.put("msg", "获取商城商品信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 查询该商品
     *
     * @param id 商品id
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/seeMallGoods")
    @ResponseBody
    public String shopSeeMallGoods(HttpServletRequest request, @RequestParam("id") String id, @RequestParam(value = "token", required = false) String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {

            if (token != null) {//这个if中的内容不清楚有没有在用
                HykUser hykUser = TokenUtil.getAppCurrentUser(token);
                String userId = hykUser.getId();
                if (userId != null) {
                    HykAddress hykAddress = hykAddressService.getDefaultAddressByUserId(userId);
                    if (hykAddress == null) {
                        List<HykAddress> list = hykAddressService.getListByUserId(userId);
                        if (list.size() > 0) {
                            hykAddress = list.get(0);
                        }
                    }
                    map.put("hykAddress", hykAddress);
                }
            }

            HykMallGoods hykMallGoods = hykMallGoodsService.get(id);
            String introcture = HtmlUtils.htmlUnescape(hykMallGoods.getIntrocture());//富文本转译
            hykMallGoods.setIntrocture(introcture);
            String mess = HtmlUtils.htmlUnescape(hykMallGoods.getMess());
            hykMallGoods.setMess(mess);
            map.put("hykMallGoods", hykMallGoods);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("获取该商品信息异常");
            map.put("code", "400");
            map.put("msg", "获取该商品信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 商城商品下订单
     *
     * @param:num 购买数量 商城订单的订单号第一位是s
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/downOrder")
    @ResponseBody
    public String shopDownOrder(HttpServletRequest request, @RequestParam(value = "token") String token,
                                @RequestParam(value = "num") Integer num, @RequestParam(value = "goodsId") String goodsId,
                                @RequestParam(value = "addressId", required = false, defaultValue = "") String addressId,
                                @RequestParam(value = "channal", required = false, defaultValue = "") String channal) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String remark = request.getParameter("remark");
            if ("ios".equals(channal)) {
                remark = new String(request.getParameter("remark").getBytes("iso-8859-1"), "utf-8");//解决ios乱码
            }
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykMallGoods hykMallGoods = hykMallGoodsService.get(goodsId);

                if ("0".equals(hykMallGoods.getGoodsType())) {//虚拟商品部分
                    if (num <= hykMallGoods.getStock() && hykMallGoods.getStock() != 0) {
                        String id = hykMallOrderService.downOrder(hykUser, hykMallGoods, num, addressId, remark);
                        HykMallOrder hykMallOrder = hykMallOrderService.get(id);
                        map.put("hykMallOrder", hykMallOrder);
                        map.put("code", "200");
                        map.put("msg", "success");
                    } else {
                        map.put("code", "201");
                        map.put("msg", "库存不足");
                    }

                } else {//实物商品部分
                    if ("1".equals(hykMallGoods.getGoodsType()) && addressId.length() > 0) {
                        if (num <= hykMallGoods.getStock() && hykMallGoods.getStock() != 0) {
                            String id = hykMallOrderService.downOrder(hykUser, hykMallGoods, num, addressId, remark);
                            HykMallOrder hykMallOrder = hykMallOrderService.get(id);
                            map.put("hykMallOrder", hykMallOrder);
                            map.put("code", "200");
                            map.put("msg", "success");
                        } else {
                            map.put("code", "201");
                            map.put("msg", "库存不足");
                        }
                    } else {
                        map.put("code", "202");
                        map.put("msg", "请选择收货地址");
                    }
                }
            }
        } catch (Exception e) {
            logger.info("商城下单异常");
            map.put("code", "400");
            map.put("msg", "商城下单异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 商城商品 实物商品（中石油加油卡中石化加油卡）下订单  放在商城下单里面判断太多了。。。
     *
     * @param:num 购买数量 商城订单的订单号第一位是s
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/downOrderOilCard")
    @ResponseBody
    public String shopDownOrderOilCard(HttpServletRequest request, @RequestParam(value = "token") String token,
                          @RequestParam(value = "num") Integer num, @RequestParam(value = "goodsId") String goodsId,
                          @RequestParam(value = "addressId", required = false, defaultValue = "") String addressId,
                          @RequestParam(value = "channal", required = false, defaultValue = "") String channal) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String remark = request.getParameter("remark");
            if ("ios".equals(channal)) {
                remark = new String(request.getParameter("remark").getBytes("iso-8859-1"), "utf-8");//解决ios乱码
            }
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykMallGoods hykMallGoods = hykMallGoodsService.get(goodsId);
                //实物商品部分
                if ("1".equals(hykMallGoods.getGoodsType()) && addressId.length() > 0) {
                    if (num <= hykMallGoods.getStock() && hykMallGoods.getStock() != 0) {
                        if (hykMallOrderService.getCountBuyOilCardByTime(hykMallGoods.getGoodsName(), userId) < 1) {
                            if (hykMallOrderService.getCountBuyOilCard(hykMallGoods.getGoodsName(), userId) < 2) {
                                String id = hykMallOrderService.downOrder(hykUser, hykMallGoods, num, addressId, remark);
                                HykMallOrder hykMallOrder = hykMallOrderService.get(id);
                                map.put("hykMallOrder", hykMallOrder);
                                map.put("code", "200");
                                map.put("msg", "success");
                            } else {
                                map.put("code", "204");
                                map.put("msg", "该种加油卡限购两张");
                            }
                        } else {
                            map.put("code", "203");
                            map.put("msg", "每个用户每日限领一张");
                        }
                    } else {
                        map.put("code", "201");
                        map.put("msg", "库存不足");
                    }
                } else {
                    map.put("code", "202");
                    map.put("msg", "请选择收货地址");
                }
            }
        } catch (Exception e) {
            logger.info("惠优商城免费油卡下单异常");
            map.put("code", "400");
            map.put("msg", "惠优商城免费油卡下单异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 商城所有订单
     *
     * @param:num 购买数量
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/allOrder")
    @ResponseBody
    public String shopAllOrder(HttpServletRequest request, @RequestParam(value = "token") String token, String sendStatus,@RequestParam(value = "currPage", required = false, defaultValue = "1") Integer currPage) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                List list = hykMallOrderService.allOrder(userId,sendStatus, currPage, pageSize);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("查看该用户所有商城订单异常");
            map.put("code", "400");
            map.put("msg", "查看该用户所有商城订单异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 商城单一订单详情
     *
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/seeOrder")
    @ResponseBody
    public String shopSeeOrder(HttpServletRequest request, @RequestParam(value = "id") String id) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykMallOrder mallOrder = hykMallOrderService.get(id);
            map.put("mallOrder", mallOrder);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("查看该商城订单异常");
            map.put("code", "400");
            map.put("msg", "查看该商城订单异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 物流查询
     *
     * @auther: 霍中曦
     * @date: 2018/11/10
     */
    @RequestMapping(value = "shop/wuLiuUtil")
    @ResponseBody
    public String shopWuLiuUtil(HttpServletRequest request, @RequestParam(value = "type") String type, @RequestParam(value = "postid") String postid, @RequestParam(value = "orderno") String orderno) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String wuliu = WuliuUtils.QueryOrder(type, postid);
            JSONObject jsStr = JSONObject.parseObject(wuliu);
            String state = jsStr.get("state").toString();
            if ("3".equals(jsStr.get("state"))) {//快递单当前签收状态，包括0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态，其中4-7需要另外开通才有效
                Integer num = hykMallOrderService.updateupdateSendStatusOver(orderno);
                logger.info(orderno + "已-----------------签-----------------收");
            }
            map.put("wuliu", wuliu);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("物流接口异常");
            map.put("code", "400");
            map.put("msg", "物流接口异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


//    /**
//     * 功能描述:
//     *
//     * @auther: 霍中曦
//     * @date: 2018/11/10
//     */
//    @RequestMapping(value = "")
//    @ResponseBody
//    public String m(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "currPage",
//            required = false, defaultValue = "1") Integer currPage) {
//        Gson gson = new Gson();
//        Map map = new HashMap();
//        try {
//            String userId = TokenUtil.getAppCurrentUser(token).getId();
//            if (userId == null || userId.length() <= 0) {
//                map.put("code", "300");
//                map.put("msg", "登录已失效 重新登录");
//            } else {
//
//                map.put("code", "200");
//                map.put("msg", "success");
//            }
//        } catch (Exception e) {
//            logger.info("获取个人所有红包信息异常");
//            map.put("code", "400");
//            map.put("msg", "success");
//            e.printStackTrace();
//        }
//        return gson.toJson(map);
//    }

}
