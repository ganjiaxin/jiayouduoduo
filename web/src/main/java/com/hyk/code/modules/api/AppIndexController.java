package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.entity.HykNotice;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.service.*;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 功能描述:App【首页】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppIndexController {
    private final static Logger logger = LoggerFactory.getLogger(AppIndexController.class);


    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykGoodsService hykGoodsService;
    @Autowired
    private HykRedpackageService hykRedpackageService;
    @Autowired
    private HykAdService hykAdService;
    @Autowired
    private HykUserImgService hykUserImgService;
    @Autowired
    private HykNoticeService hykNoticeService;


    /**
     * 功能描述: 首页banner显示
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/banner")
    @ResponseBody
    public String indexBanner() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        Map map = new HashMap();
        try {
            List<HykAd> list = hykAdService.All();
            for (int i = 0; i < list.size(); i++) {//android \u003d 无法识别 替换成=
                HykAd hykAd = list.get(i);
                if (hykAd != null) {
                    if (hykAd.getShareImg() != null) {
                        String str = hykAd.getShareImg().replace("\\u003d", "=");
                        hykAd.setShareImg(str);
                        list.set(i, hykAd);
                    }
                }
            }
            HykAd hykAd = hykAdService.getHykAdAppImg();
            map.put("hykAd", hykAd);
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取首页banner信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页banner信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 首页整合接口loop banner goods
     *
     * @auther: 霍中曦
     * @param:null
     * @return:list
     * @date: 2018/10/10
     */
    @RequestMapping(value = "index/integration")
    @ResponseBody
    public String indexIntegration() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykOrder> list1 = hykOrderService.getSuccessAll();
            List bannerList = hykAdService.All();
            HykAd hykAd = hykAdService.getHykAdAppImg();
            List immediateList = hykGoodsService.getByTypeIndex(1);//即时充值集合
            List<HykGoods> packageList = hykGoodsService.getByTypeIndex(0);//套餐充值集合
            List<HykGoods> list3 = new ArrayList<>();
            for (HykGoods hykGoods : packageList) {
                if (hykGoods.getAdviceMoney1() != null && hykGoods.getAdviceMoney2() != null) {
//                    if (hykGoods.getAdviceMoney1().compareTo(hykGoods.getAdviceMoney2()) == 0) {
                    list3.add(hykGoods);
//                    } else {
//                        HykGoods hykGoods1 = new HykGoods();
//                        BeanUtils.copyProperties(hykGoods, hykGoods1);
//                        hykGoods1.setAdviceMoney1(hykGoods.getAdviceMoney2());
//                        list3.add(hykGoods);
//                        list3.add(hykGoods1);
//                    }
                } else if (hykGoods.getAdviceMoney1() == null && hykGoods.getAdviceMoney2() == null) {
                    hykGoods.setAdviceMoney1(new BigDecimal(500));
                    list3.add(hykGoods);
                } else {
                    if (hykGoods.getAdviceMoney2() != null) {
                        hykGoods.setAdviceMoney1(hykGoods.getAdviceMoney2());
                    }
                    list3.add(hykGoods);
                }
            }
            List retrunList = list3.subList(0, 4);
            List retrunJiShi = immediateList.subList(0, 2);
            HykNotice hykNotice = hykNoticeService.findNewestTitle();
            map.put("hykNotice", hykNotice);


            map.put("code", "200");
            map.put("msg", "success");
            //成功订单滚动显示
            map.put("successOrderList", list1);
            //app广告弹窗
            map.put("hykAd", hykAd);
            //banner信息
            map.put("bannerList", bannerList);
            //即时充值集合
            map.put("immediateList", retrunJiShi);
            //套餐充值集合
            map.put("packageList", retrunList);

        } catch (Exception e) {
            logger.info("获取首页信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 首页循环显示已完成订单显示 显示数量为4条
     * 显示最新5条
     *
     * @auther: 霍中曦
     * @param:null
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/loop")
    @ResponseBody
    public String indexLoop() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykOrder> list = hykOrderService.getSuccessAll();
            HykNotice hykNotice = hykNoticeService.findNewestTitle();
            map.put("hykNotice", hykNotice);
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            logger.info("获取首页订单信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页订单信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 首页套餐显示
     * 显示根据label(热度)排序的两条数据
     *
     * @auther: 霍中曦
     * @param:type 类型 1即时充值 0套餐充值
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/goods")
    @ResponseBody
    public String indexGoods(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List list1 = hykGoodsService.getByTypeIndex(1);//即时充值集合
            List<HykGoods> list2 = hykGoodsService.getByTypeIndex(0);//套餐充值集合
//            Collections.sort(list1, new Comparator<HykGoods>() {
//                @Override
//                public int compare(HykGoods o1, HykGoods o2) {
//                    if (Integer.parseInt(o1.getLabel()) > Integer.parseInt(o2.getLabel())) {
//                        return 1;
//                    }
//                    if (o1.getLabel() .equals( o2.getLabel())) {
//                        return 0;
//                    }
//                    return -1;
//                }
//            });
//            Collections.sort(list2, new Comparator<HykGoods>() {
//                @Override
//                public int compare(HykGoods o1, HykGoods o2) {
//                    if (Integer.parseInt(o1.getLabel()) > Integer.parseInt(o2.getLabel())) {
//                        return 1;
//                    }
//                    if (o1.getLabel() .equals( o2.getLabel())) {
//                        return 0;
//                    }
//                    return -1;
//                }
//            });
            List<HykGoods> retrunList = list2.subList(0, 2);//套餐
            List<HykGoods> retrunJiShi = list1.subList(0, 2);//即时

            map.put("list1", retrunJiShi);
            map.put("list2", retrunList);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取首页套餐信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页套餐信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 首页套餐显示
     * 显示根据label(热度)排序的两条数据
     * <p>
     * 1.2.2版本之后用这个
     * <p>
     * 循环判断两个推荐金额 1 都不为空 显示两个 如果相等 显示一个
     * 2 都为空显示一个 500
     * 3 有一个为空 如果是推荐金额1为空  推荐的值赋给1
     *
     * @auther: 霍中曦
     * @param:type 类型 1即时充值 0套餐充值
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/goodsNew")
    @ResponseBody
    public String indexGoodsNew(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List list1 = hykGoodsService.getByTypeIndex(1);//即时充值集合
            List<HykGoods> list2 = hykGoodsService.getByTypeIndex(0);//套餐充值集合
            List<HykGoods> list3 = new ArrayList<>();
            for (HykGoods hykGoods : list2) {
                if (!org.springframework.util.StringUtils.isEmpty(hykGoods.getAdviceMoney1()) && !org.springframework.util.StringUtils.isEmpty(hykGoods.getAdviceMoney2())) {
                    if (hykGoods.getAdviceMoney1().compareTo(hykGoods.getAdviceMoney2()) == 0) {
                        list3.add(hykGoods);
                    } else {
                        HykGoods hykGoods1 = new HykGoods();
                        BeanUtils.copyProperties(hykGoods, hykGoods1);
                        hykGoods1.setAdviceMoney1(hykGoods.getAdviceMoney2());
                        list3.add(hykGoods);
                        list3.add(hykGoods1);
                    }
                } else if (org.springframework.util.StringUtils.isEmpty(hykGoods.getAdviceMoney1()) && org.springframework.util.StringUtils.isEmpty(hykGoods.getAdviceMoney2())) {
                    hykGoods.setAdviceMoney1(new BigDecimal(500));
                    list3.add(hykGoods);
                } else {
                    if (!org.springframework.util.StringUtils.isEmpty(hykGoods.getAdviceMoney2())) {
                        hykGoods.setAdviceMoney1(hykGoods.getAdviceMoney2());
                    }
                    list3.add(hykGoods);
                }
            }
            List retrunList = list3.subList(0, 4);//套餐
            List retrunJiShi = list1.subList(0, 2);//即时
            map.put("list1", retrunJiShi);
            map.put("list2", retrunList);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取首页套餐信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页套餐信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 详情套餐显示
     * 1.点击首页更多跳转到充值页面，显示全部商品
     * 2.同时根据 token 显示 该用户该套餐可以使用红包  token为空返回0
     *
     * @auther: 霍中曦
     * @param:type 类型 1即时充值 0套餐充值
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "details/goods")
    @ResponseBody
    public String detailsGoods(HttpServletRequest request, @RequestParam(value = "token", defaultValue = "null",
            required = false) String token,
                               @RequestParam(value = "type") Integer type) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            Integer count = 0;
            if (!token.equals("null")) {
                String userId = TokenUtil.getAppCurrentUser(token).getId();
                if (userId != null && userId.length() > 0) {
                    count = hykRedpackageService.queryAllCanCount(userId, type + "");//这里加了一个 个人可用红包个数
                }
            }
            List<HykGoods> list1 = hykGoodsService.getByType(type);//数据库充值集合
            map.put("list1", list1);
            map.put("count", count);//个人未使用红包总数
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取首页套餐信息异常");
            map.put("code", "400");
            map.put("msg", "获取首页套餐信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 分享
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/shareImg")
    @ResponseBody
    public String indexShareImg() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykAd> list = hykAdService.getHykAdShareImg();
            map.put("hykAd", list);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取分享异常");
            map.put("code", "400");
            map.put("msg", "获取分享异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 开屏广告
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/openImg")
    @ResponseBody
    public String indexOpenImg() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykAd hykAd = hykAdService.getHykAdOpenImg();
            map.put("hykAd", hykAd);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取开屏广告异常");
            map.put("code", "400");
            map.put("msg", "获取开屏广告异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: app广告弹窗  安卓说放在banner里不好用  o(￣ヘ￣o#)
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "index/appImg")
    @ResponseBody
    public String indexAppImg(@RequestParam(value = "token", defaultValue = "", required = false) String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykAd hykAd = hykAdService.getHykAdAppImg();
            if (hykAd != null) {
                if (token.length() > 0) {
                    String userId = TokenUtil.getAppCurrentUser(token).getId();
                    if (StringUtils.isNotBlank(userId)) {
                        Integer count = hykUserImgService.queryOne(userId, hykAd.getId());
                        if (count > 0) {
                            hykAd = new HykAd();
                        } else {
                            hykUserImgService.insert(userId, hykAd.getId());
                        }
                    }
                }
            }


            map.put("hykAd", hykAd);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取广告弹窗异常");
            map.put("code", "400");
            map.put("msg", "获取广告弹窗异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    //    @RequestMapping(value = "index/text")
//    @ResponseBody
//    public String text() {
//        Gson gson = new Gson();
//        Map map = new HashMap();
//        try {
//            Integer num = hykRedpackageService.sendRegisteredRedPackage("1011");
//            map.put("list", num);
//            map.put("code", "200");
//            map.put("msg", "success");
//        } catch (Exception e) {
//            logger.info("获取首页banner信息异常");
//            map.put("code", "400");
//            map.put("msg", "error");
//            e.printStackTrace();
//        }
//        return gson.toJson(map);
//    }

//    @RequestMapping(value = "index/text")
//    @ResponseBody
//    public String text() {
//        Gson gson = new Gson();
//        Map map = new HashMap();
//        try {
//            Integer num = hykRedpackageService.sendRegisteredRedPackage("1011");
//            map.put("list", num);
//            map.put("code", "200");
//            map.put("msg", "success");
//        } catch (Exception e) {
//            logger.info("获取首页banner信息异常");
//            map.put("code", "400");
//            map.put("msg", "error");
//            e.printStackTrace();
//        }
//        return gson.toJson(map);
//    }


}
