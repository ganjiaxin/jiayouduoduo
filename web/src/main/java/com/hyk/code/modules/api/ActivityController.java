package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.entity.HykAddress;
import com.hyk.code.modules.hyk.entity.HykGoods;
import com.hyk.code.modules.hyk.entity.HykInsurance;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 功能描述:活动内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */
@Controller
public class ActivityController {
    private final static Logger logger = LoggerFactory.getLogger(ActivityController.class);

    private final static String everyAmt = "300";
    private final static String startTime = "2019-03-14";//生产保险活动活动开始日期
    private final static String endTime = "2019-05-31";
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykRedpackageService hykRedpackageService;
    @Autowired
    private HykGoodsService hykGoodsService;
    @Autowired
    private HykInsuranceService hykInsuranceService;
    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykAddressService hykAddressService;


    /**
     * 功能描述: 查询这个人邀请的用户数 因为邀请活动所获得红包金额
     *
     * @auther: 霍中曦
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "activity/inviteInfo")
    @ResponseBody
    public String mineRedPackage(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Integer count = hykUserService.getCountInvite(hykUser.getPhone(), userId);
                Integer countMoney = hykRedpackageService.getCountMoneyByUserId(userId, "邀请好友赠送");
                map.put("count", count);//该用户邀请的总人数
                map.put("countMoney", countMoney);//该用户获得总邀请金额
                map.put("userId", userId);//邀请码
                map.put("phone", hykUser.getPhone());//邀请码
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
     * 2019/2/21 保险活动
     * 功能描述: 1给我token  查出 商品13月24月商品信息 以及是否符合标准给前台
     *
     * @auther: 霍中曦
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "activity/insuranceOne")
    @ResponseBody
    public String activityInsurance(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                List<HykGoods> list = hykGoodsService.getByType(0);//套餐商品集合
                String signA = "0";
                String signB = "0";
                HykGoods hykGoodsA = new HykGoods();//套餐A  13个月
                HykGoods hykGoodsB = new HykGoods();//套餐B  24个月
                List<HykInsurance> hykInsuranceA = new ArrayList<>();//套餐A保险信息
                List<HykInsurance> hykInsuranceB = new ArrayList<>();//套餐B保险信息
                for (HykGoods hykGoods : list) {
                    if (hykGoods.getCycle() == 13) {
                        hykGoodsA = hykGoods;
                        List list1 = hykOrderService.getSuccessCountByDate(userId, hykGoods.getId(), everyAmt, startTime, endTime);
                        if (list1.size() > 0) {
                            signA = "1";
                        }
                        hykInsuranceA = hykInsuranceService.getByUserIdAndGoodsId(userId, hykGoods.getId());
                        if (hykInsuranceA.size() > 0) {
                            signA = "2";
                        }
                    }
                    if (hykGoods.getCycle() == 24) {
                        hykGoodsB = hykGoods;
                        List list1 = hykOrderService.getSuccessCountByDate(userId, hykGoods.getId(), everyAmt, startTime, endTime);
                        if (list1.size() > 0) {
                            signB = "1";
                        }
                        hykInsuranceB = hykInsuranceService.getByUserIdAndGoodsId(userId, hykGoods.getId());
                        if (hykInsuranceB.size() > 0) {
                            signB = "2";
                        }
                    }
                }
                HykAddress hykAddress = hykAddressService.getDefaultAddressByUserId(userId);
                if(StringUtils.isEmpty(hykAddress)){
                    hykAddress=new HykAddress();
                    hykAddress.setPhone("");
                    hykAddress.setAddress("");
                    hykAddress.setName("");
                }
                map.put("hykAddress", hykAddress);//该用户默认地址
                map.put("signA", signA);//套餐A保险状态  0未达标需加油  1已达标点击领取保险 2已领取点击查看
                map.put("signB", signB);
                map.put("hykGoodsA", hykGoodsA);//套餐A
                map.put("hykGoodsB", hykGoodsB);
                map.put("hykInsuranceA", hykInsuranceA);//保险优选方案A
                map.put("hykInsuranceB", hykInsuranceB);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取保险活动内容异常");
            map.put("code", "400");
            map.put("msg", "获取保险活动内容异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 2019/2/21  保存保险活动信息
     * 功能描述: 1给我token 及相关内容  保存这些内容  如果地址相关信息与地址管理内容不同 保存一份到地址信息
     *
     * @auther: 霍中曦
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "activity/insuranceSave")
    @ResponseBody
    public String activityInsuranceSave(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "plateNumber") String plateNumber,
                                        @RequestParam(value = "brandModel") String brandModel, @RequestParam(value = "vehicleIdentificationCode") String vehicleIdentificationCode,
                                        @RequestParam(value = "engineNumber") String engineNumber, @RequestParam(value = "registerDate") String registerDate, @RequestParam(value = "name") String name,
                                        @RequestParam(value = "idNumber") String idNumber, @RequestParam(value = "goodsId") String goodsId, @RequestParam(value = "addressName") String addressName,
                                        @RequestParam(value = "addressPhone") String addressPhone, @RequestParam(value = "address") String address) {
        Gson gson = new GsonBuilder().serializeNulls().create();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykGoods hykGoods = hykGoodsService.get(goodsId);
                if (StringUtils.isEmpty(hykGoods) || (hykGoods.getCycle() != 13 && hykGoods.getCycle() != 24)) {
                    map.put("code", "601");
                    map.put("msg", "不符合活动要求");
                    return gson.toJson(map);
                }
                List list1 = hykOrderService.getSuccessCountByDate(userId, goodsId, everyAmt, startTime, endTime);
                if (list1.size() <= 0) {
                    map.put("code", "601");
                    map.put("msg", "不符合活动要求");
                    return gson.toJson(map);
                }
                List list2 = hykInsuranceService.getByUserIdAndGoodsId(userId, hykGoods.getId());
                if (list2.size() > 0) {
                    map.put("code", "602");
                    map.put("msg", "请不要重复提交");
                    return gson.toJson(map);
                }

                List<HykGoods> list = hykGoodsService.getByType(0);//套餐商品集合
                List<HykInsurance> hykInsuranceA = new ArrayList<>();//套餐A保险信息
                List<HykInsurance> hykInsuranceB = new ArrayList<>();//套餐B保险信息
                for (HykGoods hykGoodsL : list) {
                    if (hykGoodsL.getCycle() == 13) {
                        hykInsuranceA = hykInsuranceService.getByUserIdAndGoodsId(userId, hykGoodsL.getId());
                    }
                    if (hykGoods.getCycle() == 24) {
                        hykInsuranceB = hykInsuranceService.getByUserIdAndGoodsId(userId, hykGoodsL.getId());
                    }
                }
                if(hykInsuranceA.size()+hykInsuranceB.size()>=1){
                    map.put("code", "602");
                    map.put("msg", "请不要重复提交");
                    return gson.toJson(map);
                }

                HykInsurance hykInsurance = new HykInsurance();
                hykInsurance.setUserId(userId);
                hykInsurance.setPlateNumber(plateNumber);
                hykInsurance.setBrandModel(brandModel);
                hykInsurance.setVehicleIdentificationCode(vehicleIdentificationCode);
                hykInsurance.setEngineNumber(engineNumber);
                DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
                Date date = format1.parse(registerDate);
                hykInsurance.setRegisterDate(date);
                hykInsurance.setName(name);
                hykInsurance.setIdNumber(idNumber);
                hykInsurance.setCreateDate(new Date());
                hykInsurance.setGoodsId(goodsId);
                hykInsurance.setStatus("0");
                hykInsurance.setDelFlag("0");
                hykInsurance.setConsignee(addressName);
                hykInsurance.setAddress(address);
                hykInsurance.setPhone(addressPhone);
                // hykInsurance.setRemarks();
                hykInsuranceService.save(hykInsurance);

                HykAddress hykAddressNew = new HykAddress();
                hykAddressNew.setName(addressName);
                hykAddressNew.setAddress(address);
                hykAddressNew.setPhone(addressPhone);
                hykAddressNew.setUserId(userId);
                hykAddressNew.setIsDefault("0");
                HykAddress hykAddress = hykAddressService.getByHykAddress(hykAddressNew);
                if (StringUtils.isEmpty(hykAddress)) {
                    hykAddressService.addAddress(hykAddressNew);
                }

                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("投保信息保存异常");
            map.put("code", "400");
            map.put("msg", "请按提示格式填写信息");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
