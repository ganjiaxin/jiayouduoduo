package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.entity.HykAddress;
import com.hyk.code.modules.hyk.entity.HykMallGoods;
import com.hyk.code.modules.hyk.service.HykAddressService;
import com.hyk.code.modules.hyk.service.HykMallGoodsService;
import com.hyk.code.modules.hyk.service.HykMallOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2019/1/10 18:11
 * @Description:
 */
@Controller
@RequestMapping(value = "${frontPath}/web/oil/")
public class AppOilClaimController {


    @Autowired
    private HykMallGoodsService hykMallGoodsService;
    @Autowired
    private HykMallOrderService hykMallOrderService;
    @Autowired
    private HykAddressService hykAddressService;

    @RequestMapping(value = "oilPage")
    @ResponseBody
    public String list(@RequestParam(value = "token")String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            if (!token.equals("null")) {
               String userId = TokenUtil.getAppCurrentUser(token).getId();

                if (userId == null || userId.length() <= 0) {
                    map.put("code", "300");
                    map.put("msg", "登录已失效 重新登录");
                } else {
                    HykAddress hykAddress=new HykAddress();
                    hykAddress.setUserId(userId);
                    hykAddress.setIsDefault("1");
                    HykAddress address=hykAddressService.getDefaultAddressByUserId(userId);

                    HykMallGoods hykMallGoods=new HykMallGoods();
                    hykMallGoods.setCategory("5");
                    List<HykMallGoods> list=hykMallGoodsService.findList(hykMallGoods);
                    List<HykMallGoods> newList=new ArrayList<HykMallGoods>(2);
                    for (int i=0;i<list.size();i++){
                        HykMallGoods newhykMallGoods=new HykMallGoods();
                        newhykMallGoods.setGoodsName(list.get(i).getGoodsName());
                        newhykMallGoods.setId(list.get(i).getId());
                        newhykMallGoods.setStock(list.get(i).getStock());
                        if(list.get(i).getGoodsName().equals("中石化加油卡")){
                            newList.add(newhykMallGoods);
                        }

                    }
                    for (int i=0;i<list.size();i++){
                        HykMallGoods newhykMallGoods=new HykMallGoods();
                        newhykMallGoods.setGoodsName(list.get(i).getGoodsName());
                        newhykMallGoods.setId(list.get(i).getId());
                        newhykMallGoods.setStock(list.get(i).getStock());
                        if(list.get(i).getGoodsName().equals("中石油加油卡")){
                            newList.add(newhykMallGoods);
                        }
                    }
                    map.put("code", "200");
                    map.put("data", newList);

                    if(address!=null){
                        map.put("address",address.getAddress());
                    }else{
                        map.put("address","");
                    }
                    List<String> ruleList=new ArrayList<String>();
                    ruleList.add("1、领取时间:从即日起，长期有效；");
                    ruleList.add("2、活动期间，每位用户每次限领一张，最多可领4张（中石化和中石油各两张）；");
                    ruleList.add("3、油卡均为百德网公司副卡，每张副卡均单独使用，但不具备在其他地方充值的功能，仅限在加油多多内进行充值;用户购买油卡发货时，自动绑定；");
                    ruleList.add("4、活动期间，用户需要支付固定的9.9元运费，付款成功后暂不支持退款；");
                    ruleList.add("5、用户选择领取的油卡类型，添加收货地址；用户需要手动输入正确的收货人姓名、电话和地址，确保加油卡准确送达；");
                    ruleList.add("6、油卡在用户付款成功后的3-5个工作日内寄出，寄出后以短信和站内信等形式通知用户，请注意查收；");
                    ruleList.add("7、如有任何疑问请咨询客服人员:****-***-***；");
                    ruleList.add("8、本次领取油卡的最终解释权归加油多多所有。");
                    map.put("rule",ruleList);
                }
            }
        } catch (Exception e) {
            map.put("code", "400");
            map.put("msg", "异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
