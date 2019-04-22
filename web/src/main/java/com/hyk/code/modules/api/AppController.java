package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.common.utils.*;
import com.hyk.code.common.utils.qrcode.ZxingCode;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.entity.HykYzm;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import com.hyk.code.modules.hyk.service.HykUserService;
import com.hyk.code.modules.hyk.service.HykYzmService;
import com.hyk.code.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 移动端公共接口
 *
 * @auther: 霍中曦
 * @param:
 * @return:
 * @date: 2018/11/7 14:35
 */
@Controller
@RequestMapping(value = "${frontPath}/app/")
public class AppController extends BaseController {

    @Autowired
    private HykUserService hykUserService;
    @Autowired
    private HykYzmService hykYzmService;
    @Autowired
    private HykRedpackageService hykRedpackageService;

    private static PropertiesLoader loader = new PropertiesLoader("application.properties");
    public static final String console_hyk=loader.getProperty("hyk-admin");

    @RequestMapping(value = "inter")
    public String inter(HykYzm hykYzm, HttpServletRequest request) {
        return "index";
    }

    @RequestMapping(value = "index")
    @ResponseBody
    public String index(HykYzm hykYzm, HttpServletRequest request) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        map.put("code", "200");
        map.put("msg", "菜单请求成功");

        return gson.toJson(map);
    }


    /**
     * 功能描述:检查手机号是否存在
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/9 14:41
     */
    @RequestMapping(value = "checkPhone")
    @ResponseBody
    public String checkPhone(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (hykUser.getPhone() != null) {
                System.out.println(hykUser.getPhone());
                if (VliateUser.isPhone(hykUser.getPhone())) {
                    HykUser registerUser = new HykUser();
                    registerUser.setPhone(hykUser.getPhone());
                    HykUser old = hykUserService.getByPhone(registerUser);
                    if (old != null) {
                        map.put("code", "402");
                        map.put("msg", "用户名已存在！");
                    } else {
                        map.put("code", "200");
                        map.put("msg", "用户名不存在！");
                    }
                } else {
                    map.put("code", "401");
                    map.put("msg", "手机号不符合规则！");
                }

            } else {
                map.put("code", "400");
                map.put("msg", "手机号为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "500");
            map.put("msg", "接口异常！");
        }

        return gson.toJson(map);
    }

    /**
     * 获取短信验证码
     * type  1注册 2找回密码
     */
    @RequestMapping(value = "getCode")
    @ResponseBody
    public String getCode(HttpServletRequest request, @RequestParam(value = "phone") String phone, @RequestParam(value = "type") Integer type) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            String ip = request.getHeader("x-forwarded-for");
            if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                ip = request.getHeader(" Proxy-Client-IP ");
            }
            if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                ip = request.getHeader(" WL-Proxy-Client-IP ");
            }
            if (ip == null || ip.length() == 0 || " unknown ".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            Integer count=hykYzmService.queryCountByIp(ip);
            if(count==100){
                HykYzm hykYzm = new HykYzm();
                hykYzm.setPhone(phone);
                hykYzm.setType(type);
                hykYzm.setTimes(System.currentTimeMillis() / 1000 + 120);//-------------------------------------------------------------------------有效时长 900 15分钟  600 10分钟
                String code = ZxingHandler.getVerificationCode(4);
                hykYzm.setCode(Long.valueOf(code));//------------------------------------------------------------------------验证码 先保存验证码相关信息到数据库  后期会根据该验证码做验证
                hykYzm.setCreateDate(new Date());
                hykYzm.setIp(ip);
                hykYzmService.saveYzm(hykYzm);

                map.put("code", "402");
                map.put("msg", "该IP请求短信过多，请明日再试，或联系客服！");
                SmsUtil.sendErrorToUs("刷短信100条ip"+ip,request.getServletPath());
                return gson.toJson(map);
            }
            if(count>100){
                map.put("code", "402");
                map.put("msg", "该IP请求短信过多，请明日再试，或联系客服！");
                return gson.toJson(map);
            }
            if(count==50){
                SmsUtil.sendErrorToUs("刷短信50条ip"+ip,request.getServletPath());
            }
            HykYzm hykYzm = new HykYzm();
            hykYzm.setPhone(phone);
            hykYzm.setType(type);
            hykYzm.setTimes(System.currentTimeMillis() / 1000 + 120);//-------------------------------------------------------------------------有效时长 900 15分钟  600 10分钟
            String code = ZxingHandler.getVerificationCode(4);
            hykYzm.setCode(Long.valueOf(code));//------------------------------------------------------------------------验证码 先保存验证码相关信息到数据库  后期会根据该验证码做验证
            hykYzm.setCreateDate(new Date());
            hykYzm.setIp(ip);
            hykYzmService.saveYzm(hykYzm);

            //可以发短信啦
            SmsUtils.mobileQuery(phone, code);


            map.put("code", "200");
            map.put("msg", "success");
        } catch (Exception e) {
            logger.info("获取短信验证码异常");
            map.put("code", "400");
            map.put("msg", "获取短信验证码异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:比对验证码
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/9 14:41
     */
    @RequestMapping(value = "checkCode")
    @ResponseBody
    public String checkCode(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model, @RequestParam("userCode") String userCode) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (hykUser.getPhone() != null) {
                System.out.println(hykUser.getPhone());
                if (VliateUser.isPhone(hykUser.getPhone())) {
                    HykUser registerUser = new HykUser();
                    registerUser.setPhone(hykUser.getPhone());
                    HykYzm hykYzm = hykYzmService.queryByPhone(hykUser.getPhone());
                    if (System.currentTimeMillis() / 1000 < hykYzm.getTimes()) {
                        String kuCode = hykYzm.getCode() + "";
                        if (kuCode.equals(userCode)) {
                            map.put("code", "200");
                            map.put("msg", "验证码正确！");
                        } else {
                            map.put("code", "202");
                            map.put("msg", "验证码错误！");
                            hykUserService.updateCode(hykUser.getPhone());
                        }
                    } else {
                        map.put("code", "203");
                        map.put("msg", "验证码超时！");
                    }
                } else {
                    map.put("code", "401");
                    map.put("msg", "用户名或密码不正确！");
                }
            } else {
                map.put("code", "400");
                map.put("msg", "用户名或密码为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "500");
            map.put("msg", "接口异常！");
        }

        return gson.toJson(map);
    }

    /**
     * 功能描述:注册接口
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/9 14:41
     */
    @RequestMapping(value = "register")
    @ResponseBody
    public String register(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        String path = request.getSession().getServletContext().getRealPath("/");
        try {
            if (hykUser.getPhone() != null && hykUser.getPassword() != null) {
                if (VliateUser.isPhone(hykUser.getPhone()) && VliateUser.ValidatePassword(hykUser.getPassword())) {
                    HykUser registerUser = new HykUser();
                    registerUser.setPhone(hykUser.getPhone());
                    HykUser old = hykUserService.getByPhone(registerUser);
                    if (old != null) {
                        map.put("code", "402");
                        map.put("msg", "用户名已存在！");
                    } else {
                        if (hykUser.getInviterId() != null && hykUser.getInviterId().length() > 0) {
                            if (hykUser.getInviterId().length() == 11) {//将手机号邀请码转换成id
                                HykUser hykUser1 = new HykUser();
                                hykUser1.setPhone(hykUser.getInviterId());
                                HykUser hykUser2 = hykUserService.getByPhone(hykUser1);
                                if (hykUser2 != null) {
                                    String id = hykUser2.getId();
                                    hykUser.setInviterId(id);
                                }
                            }
                            if (hykUserService.get(hykUser.getInviterId()) == null) {
                                map.put("code", "403");
                                map.put("msg", "邀请人未注册或填写错误，请确认后重新输入");
                                return gson.toJson(map);
                            }
                        }

                        hykUser.setPassword(SystemService.entryptPassword(hykUser.getPassword()));
                        hykUser.setRegisterDate(new Date());
                        hykUser.setSendCodeError(0L);
                        hykUser.setAccountError(0L);
                        hykUser.setOilCardNum(0L);
                        hykUser.setWaitAmt(BigDecimal.ZERO);
                        hykUser.setPayNum(0L);

                        hykUser.setAccumulativeRechargeAmount(BigDecimal.ZERO);
                        hykUser.setAccountBalance(BigDecimal.ZERO);
                        hykUser.setIsCompany("0");
                        hykUser.setAccountBalance(BigDecimal.ZERO);
                        hykUser.setAccumulativeRechargeAmount(BigDecimal.ZERO);
                        //SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        //Date date = sdf.parse("1970-01-01 08:00:00");
                        //hykUser.setBirthday(date);
                        hykUserService.save(hykUser);

                        String qrcode=ZxingCode.registeredTwoDimensional(path,hykUser.getPhone(),hykUser.getId());
                        hykUser.setQrcode(qrcode);

                        hykUserService.save(hykUser);
//-------------------------------------------------注册相关活动区域-----------------------------------------------------
                        hykRedpackageService.sendRegisteredRedPackage(hykUser.getId(), hykUser.getPhone());//新手注册红包 6个10元的1个8元的
//----------------------------------------------------------------------------------------------------------------------

                        map.put("code", "200");
                        map.put("msg", "注册成功！");
                        map.put("phone", hykUser.getPhone());
                        map.put("id", hykUser.getId());
                        map.put("token", TokenUtil.getToken(hykUser.getPhone(), hykUser.getPassword(), hykUser.getId()));
                        map.put("inviterId", hykUser.getInviterId());
                    }

                } else {
                    map.put("code", "401");
                    map.put("msg", "用户名或密码格式不正确！");
                }

            } else {
                map.put("code", "400");
                map.put("msg", "用户名或密码为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "500");
            map.put("msg", "接口异常！");
            SmsUtil.sendErrorToUs("注册",request.getServletPath());
        }

        return gson.toJson(map);
    }

    /**
     * 功能描述: pc登录功能
     *
     * @auther: 霍中曦
     * @param: [hykUser, request, response, model]
     * @return: java.lang.String
     * @date: 2018/11/9 13:52
     */
    @RequestMapping(value = "login")
    @ResponseBody
    public String login(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (hykUser.getPhone() != null) {
                HykUser registerUser = new HykUser();
                registerUser.setPhone(hykUser.getPhone());
                HykUser old = hykUserService.getByPhone(registerUser);
                if (old != null) {
//                    if (old.getAccountError() <= 5) {
//                        if (old.getSendCodeError() <= 5) {
                            String password = hykUser.getPassword();
                            String entryptPassword = old.getPassword();
                            if (SystemService.validatePassword(password, entryptPassword)) {
                                map.put("code", "200");
                                map.put("msg", "登录成功！");
                                map.put("realName", old.getRealName());
                                map.put("phone", old.getPhone());
                                map.put("id", old.getId());
                                map.put("token", TokenUtil.getToken(old.getPhone(), old.getPassword(), old.getId()));
                            } else {
                                map.put("msg", "密码不正确！");
                                map.put("code", "401");
                                hykUserService.updateAccount(hykUser.getPhone());
                            }
//                        } else {
//                            map.put("msg", "验证码错误次数过多,请联系客服处理");
//                            map.put("code", "411");
//                        }
//                    } else {
//                        map.put("msg", "登陆错误次数过多,请联系客服处理");
//                        map.put("code", "410");
//                    }
                } else {
                    map.put("msg", "用户不存在！");
                    map.put("code", "402");
                }

            } else {
                map.put("code", "400");
                map.put("msg", "登录手机号为空！");
            }

        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "500");
            map.put("msg", "系统异常！");
            SmsUtil.sendErrorToUs("登录",request.getServletPath());
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:忘记密码修改密码
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/11/9 14:41
     */
    @RequestMapping(value = "updatepwd")
    @ResponseBody
    public String updatepwd(HykUser hykUser, HttpServletRequest request, HttpServletResponse response, Model model) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try {
            if (hykUser.getPhone() != null && hykUser.getPassword() != null) {
                if (VliateUser.isPhone(hykUser.getPhone()) && VliateUser.ValidatePassword(hykUser.getPassword())) {
                    HykUser registerUser = new HykUser();
                    registerUser.setPhone(hykUser.getPhone());
                    HykUser old = hykUserService.getByPhone(registerUser);
                    if (old == null) {
                        map.put("code", "402");
                        map.put("msg", "用户名不存在！");
                    } else {
                        hykUser.setPassword(SystemService.entryptPassword(hykUser.getPassword()));
                        hykUserService.updatePwd(hykUser);
                        map.put("code", "200");
                        map.put("msg", "修改成功！");
//                        map.put("phone", hykUser.getPhone());
//                        map.put("id", hykUser.getId());
//                        map.put("token", TokenUtil.getToken(hykUser.getPhone(), hykUser.getPassword(), hykUser.getId()));
                    }

                } else {
                    map.put("code", "401");
                    map.put("msg", "用户名或密码格式不正确！");
                }

            } else {
                map.put("code", "400");
                map.put("msg", "用户名或密码为空！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", "500");
            map.put("msg", "接口异常！");
        }

        return gson.toJson(map);
    }


    /**
     * 功能描述: 下载后台图片
     *
     * @auther: 霍中曦
     * @param:
     * @return:
     * @date: 2018/12/27 13:55
     */
    @RequestMapping(value = "download/image")
    public void inter(String filePath, HttpServletResponse response) {
        HttpClientDownloadImg.downloadImg(filePath, response,console_hyk);
    }


    /*生成二维码*/
    @RequestMapping(value = "createQrcode")
    @ResponseBody
    public String createQrcode(String id,HttpServletRequest request, HttpServletResponse response) {
        Gson gson = new Gson();
        Map<String, String> map = new HashMap<String, String>();
        try{
            String path = request.getSession().getServletContext().getRealPath("/");
            HykUser hykUser=hykUserService.get(id);
            String qrcode=ZxingCode.registeredTwoDimensional(path,hykUser.getPhone(),hykUser.getId());
            hykUser.setQrcode(qrcode);
            hykUserService.save(hykUser);
            map.put("code","200");
        }catch (Exception e){
            e.printStackTrace();
            map.put("code","400");
        }
        return gson.toJson(map);
    }


}