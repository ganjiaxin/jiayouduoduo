package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.ZxingHandler;
import com.hyk.code.common.utils.qrcode.ZxingCode;
import com.hyk.code.modules.hyk.entity.*;
import com.hyk.code.modules.hyk.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【我的】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppMyController {
    private final static Logger logger = LoggerFactory.getLogger(AppMyController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykOrderService hykOrderService;
    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykAddressService hykAddressService;
    @Autowired
    private HykAdviceService hykAdviceService;
    @Autowired
    private HykNoticeService hykNoticeService;
    @Autowired
    private HykAppVersionService hykAppVersionService;


    /**
     * 功能描述: 我的->个人信息->地址信息    查看个人所有地址
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/seeAddress")
    @ResponseBody
    public String mineSeeAddress(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                List list = hykAddressService.getListByUserId(userId);
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取地址信息异常");
            map.put("code", "400");
            map.put("msg", "获取地址信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 我的->个人信息->地址信息    返回单条地址
     *
     * @auther: 霍中曦
     * @param:addressId 地址Id
     * @return:
     * @date: 2018/11/10
     * <p>
     * 明明可以自己带着信息的。。。
     */
    @RequestMapping(value = "mine/oneAddress")
    @ResponseBody
    public String mineOneAddress(HttpServletRequest request, @RequestParam(value = "addressId", required = false,defaultValue = "") String addressId,
                                 @RequestParam(value = "token", required = false,defaultValue = "") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykAddress hykAddress = new HykAddress();
            if (addressId.length()<=0 && token.length()>0) {
                HykUser hykUser = TokenUtil.getAppCurrentUser(token);
                //if (token != null) {
                String userId = hykUser.getId();
                if (userId != null) {
                     hykAddress = hykAddressService.getDefaultAddressByUserId(userId);
                    if (hykAddress == null) {
                        List<HykAddress> list = hykAddressService.getListByUserId(userId);
                        if (list.size() > 0) {
                            hykAddress = list.get(0);
                        }
                    }
                    //map.put("hykAddress", hykAddress);
                }
                //}
            } else {
                hykAddress = hykAddressService.getByAddressId(addressId);
            }
            map.put("hykAddress", hykAddress);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (
                Exception e) {
            logger.info("获取该条地址信息异常");
            map.put("code", "400");
            map.put("msg", "获取该条地址信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 实物商品下单 android 想要一个只返回一条地址信息的接口
     *
     * @auther: 霍中曦
     * @param:addressId 地址Id
     * @return:
     * @date: 2018/11/10
     * <p>
     */
    @RequestMapping(value = "mine/getOneAddress")
    @ResponseBody
    public String mineGetOneAddress(HttpServletRequest request, @RequestParam(value = "token", required = false) String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykAddress hykAddress = new HykAddress();
            if (token != null) {
                HykUser hykUser = TokenUtil.getAppCurrentUser(token);
                String userId = hykUser.getId();
                if (userId != null) {
                    hykAddress = hykAddressService.getDefaultAddressByUserId(userId);
                    if (hykAddress == null) {
                        List<HykAddress> list = hykAddressService.getListByUserId(userId);
                        if (list.size() > 0) {
                            hykAddress = list.get(0);
                        }
                    }

                }
            }
            map.put("hykAddress", hykAddress);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取默认地址异常");
            map.put("code", "400");
            map.put("msg", "获取默认地址异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 修改地址信息
     *
     * @auther: 霍中曦
     * @param:addressId 地址id
     * @param:name 姓名
     * @param:phone 手机号
     * @param:address 地址
     * @param:isDefault 是否默认 0否1是
     * @return:
     * @date: 2018/11/10
     * 如果把该条记录设为默认 先把所有该用户的地址设为非默认-->设置该id的地址为默认
     */
    @RequestMapping(value = "mine/updateAdd")
    @ResponseBody
    public String mineUpdateAdd(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam("addressId") String addressId,
                                @RequestParam("name") String name, @RequestParam("phone") String phone, @RequestParam("address") String address, @RequestParam("isDefault") String isDefault) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                if (isDefault.equals("1")) {
                    hykAddressService.updateNotDefault(userId);
                }
                HykAddress hykAddress = new HykAddress();
                hykAddress.setId(addressId);
                hykAddress.setName(name);
                hykAddress.setPhone(phone);
                hykAddress.setAddress(address);
                hykAddress.setIsDefault(isDefault);
                Integer num1 = hykAddressService.updateAddress(hykAddress);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("设置地址信息异常");
            map.put("code", "400");
            map.put("msg", "设置地址信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 添加地址信息
     *
     * @auther: 霍中曦
     * @param:name 姓名
     * @param:phone 手机号
     * @param:address 地址
     * @param:isDefault 是否默认 0否1是
     * @return:
     * @date: 2018/11/10
     * 如果把该条记录设为默认 先把所有该用户的地址设为非默认-->设置该id的地址为默认
     */
    @RequestMapping(value = "mine/addAddress")
    @ResponseBody
    public String mineAddAddress(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam("name") String name,
                                 @RequestParam("phone") String phone, @RequestParam("address") String address, @RequestParam("isDefault") String isDefault) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                if (isDefault.equals("1")) {
                    hykAddressService.updateNotDefault(userId);
                }
                HykAddress hykAddress = new HykAddress();
                hykAddress.setUserId(userId);
                hykAddress.setName(name);
                hykAddress.setPhone(phone);
                hykAddress.setAddress(address);
                hykAddress.setIsDefault(isDefault);
                Integer num1 = hykAddressService.addAddress(hykAddress);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("添加地址异常");
            map.put("code", "400");
            map.put("msg", "添加地址异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:  删除地址信息
     *
     * @auther: 霍中曦
     * @param:addressId 地址id
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/delAdd")
    @ResponseBody
    public String mineDelAdd(HttpServletRequest request, @RequestParam(value = "addressId") String addressId) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            Integer num = hykAddressService.softDeleteAddress(addressId);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("删除地址信息异常");
            map.put("code", "400");
            map.put("msg", "删除地址信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 个人中心信息展示
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/info")
    @ResponseBody
    public String mineInfo(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                HykUser hykUser = hykUserService.queryInfo(userId);
                String str=hykUser.getInviterId();
                if (hykUser.getQrcode().length() == 0) {
                    String path = request.getSession().getServletContext().getRealPath("/");
                    String qrcode = ZxingCode.registeredTwoDimensional(path, hykUser.getPhone(), hykUser.getInviterId());
                    hykUser.setQrcode(qrcode);
                    hykUser.setInviterId(hykUser.getRealInviterId());//解决版本兼容
                    hykUser.setId(userId);
                    hykUserService.save(hykUser);
                    hykUser.setInviterId(str);
                }
                //hykUser = hykUserService.queryInfo(userId);
                map.put("hykUser", hykUser);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取个人中心信息异常");
            map.put("code", "400");
            map.put("msg", "获取个人中心信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:  修改个人中心信息
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/updateInfo")
    @ResponseBody
    public String mineUpdateInfo(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                hykUser.setId(userId);
                hykUserService.save(hykUser);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("修改个人中心异常");
            map.put("code", "400");
            map.put("msg", "修改个人中心异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述:  ios更改生日
     *
     * @auther: 霍中曦
     * @param:str 生日的时间戳秒类型
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/updateBirthday")
    @ResponseBody
    public String mineBirthday(String str, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            String userId = TokenUtil.getAppCurrentUser(token).getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Integer integer = hykUserService.updateBirthday(str, userId);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("修改个人中心异常");
            map.put("code", "400");
            map.put("msg", "修改个人中心异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 所有公告
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/notice")
    @ResponseBody
    public String mineNotice(HttpServletRequest request, @RequestParam(value = "currPage", defaultValue = "1", required = false) Integer currPage) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List list = hykNoticeService.findAll(currPage, pageSize);
            map.put("list", list);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("查询用户所有公告异常");
            map.put("code", "400");
            map.put("msg", "查询用户所有公告异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 根据id 查公告
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/getNotice")
    @ResponseBody
    public String mineNotice(HttpServletRequest request, @RequestParam(value = "id") String id) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykNotice notice = hykNoticeService.get(id);
            map.put("notice", notice);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("查询该条公告异常");
            map.put("code", "400");
            map.put("msg", "查询该条公告异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 最新公告标题
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/newestTitle")
    @ResponseBody
    public String mineNewestTitle(HttpServletRequest request) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykNotice hykNotice = hykNoticeService.findNewestTitle();
            map.put("hykNotice", hykNotice);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("查询最新公告标题异常");
            map.put("code", "400");
            map.put("msg", "查询最新公告标题异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 意见反馈
     *
     * @auther: 霍中曦
     * @param:opinion 意见内容
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/opinion")
    @ResponseBody
    public String mineOpinion(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "token") String token,
                              @RequestParam(value = "content") String content, @RequestParam(value = "imgs", required = false) String imgs) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                Integer num = hykAdviceService.saveOpinion(ZxingHandler.getUUID(), content, imgs, hykUser.getPhone());
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("意见反馈保存异常");
            map.put("code", "400");
            map.put("msg", "意见反馈保存异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 获取app版本信息
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     * <p>
     * 只有安卓的版本信息  数据字典中id =android的那条数据
     */
    @RequestMapping(value = "app/version")
    @ResponseBody
    public String appVersion() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            //Map map1 = hykOrderService.getApp("android");
            HykAppVersion hykAppVersion=hykAppVersionService.getBySystem("1");
            map.put("app", hykAppVersion);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取安卓APP版本信息异常");
            map.put("code", "400");
            map.put("msg", "获取安卓APP版本信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 获取app版本信息
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     * <p>
     * 只有安卓的版本信息  数据字典中id=ios的那条数据
     */
    @RequestMapping(value = "app/versionIos")
    @ResponseBody
    public String appVersionIos() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            //Map map1 = hykOrderService.getApp("ios");
            HykAppVersion hykAppVersion=hykAppVersionService.getBySystem("0");
            map.put("app", hykAppVersion);
            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取苹果APP版本信息异常");
            map.put("code", "400");
            map.put("msg", "获取苹果APP版本信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 获取app版本信息
     *
     * @auther: 霍中曦
     * @param:system 操作系统
     * @return:
     * @date: 2018/11/10
     */
/*    @RequestMapping(value = "app/NewVersion")
    @ResponseBody
    public String appNewVersion(String system) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykAppVersion hykAppVersion=hykAppVersionService.getBySystem(system);
            map.put("code", "200");
            map.put("msg", "success");
            map.put("hykAppVersion", hykAppVersion);
        } catch (Exception e) {
            logger.info("获取最新APP版本信息异常");
            map.put("code", "400");
            map.put("msg", "error");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }*/

    /**
     * 功能描述: 被邀请用户查询
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/17 16:51
     */
    @RequestMapping(value = "mine/inviterUser")
    @ResponseBody
    public String mineInviterUser(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            if (hykUser.getId() == null || hykUser.getId().length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                String inviterCode = hykUser.getId();
                List list = hykUserService.selectInviterUser(inviterCode, hykUser.getPhone());
                Integer count = list.size();
                map.put("inviterCode", inviterCode);//我的邀请码
                map.put("count", count);//用户数
                map.put("list", list);//用户信息
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("邀请用户查询失败");
            map.put("code", "400");
            map.put("msg", "邀请用户查询失败");
            e.printStackTrace();
        }


        return gson.toJson(map);
    }

    /**
     * 功能描述:被邀请用的返现记录
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/17 16:51
     */
    @RequestMapping(value = "myCenter/inviterUser")
    @ResponseBody
    public String inviterUserBackMoney(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            if (hykUser.getId() == null || hykUser.getId().length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                String inviterCode = hykUser.getId();
                HykOrder hykorder = new HykOrder();
                hykorder.setUserId(inviterCode);
                HykOrder totalMoney = hykOrderService.sumBackMoney(hykorder);
                List<HykOrder> monthlist = hykOrderService.monthList(hykorder);
                for (int i = 0; i < monthlist.size(); i++) {
                    hykorder.setMonth(monthlist.get(i).getMonth());
                    List<HykOrder> list = hykOrderService.queryInviterBackMoney(hykorder);
                    monthlist.get(i).setMonths(list);
                }
                if (totalMoney != null && totalMoney.getBackMoney() != null) {
                    map.put("totalMoney", totalMoney.getBackMoney());//返现总金额
                } else {
                    map.put("totalMoney", 0);//返现总金额
                }
                map.put("monthlist", monthlist);//返现记录
                map.put("userId", inviterCode);//用户邀请码
            }
        } catch (Exception e) {
            logger.info("邀请用户返现记录查询失败");
            map.put("code", "400");
            map.put("msg", "邀请用户返现记录查询失败");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述: 判断是否登录
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "mine/lognStatus")
    @ResponseBody
    public String mineLognStatus(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            String userId = hykUser.getId();
            if (userId == null || userId.length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("判断是否登录异常");
            map.put("code", "400");
            map.put("msg", "判断是否登录异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }
}
