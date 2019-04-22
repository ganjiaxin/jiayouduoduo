package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykNews;
import com.hyk.code.modules.hyk.entity.HykOilPrice;
import com.hyk.code.modules.hyk.entity.HykOrder;
import com.hyk.code.modules.hyk.service.*;
import freemarker.template.utility.StringUtil;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:App【发现】内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppFindController {
    private final static Logger logger = LoggerFactory.getLogger(AppFindController.class);
    private final static Integer pageSize = 10;


    @Autowired
    private HykAdService hykAdService;
    @Autowired
    private HykOilPriceService hykOilPriceService;
    @Autowired
    private HykNewsService hykNewsService;

    /**
     * 功能描述:  该省最新油价
     *
     * @auther: 霍中曦
     * @param:prov 省份
     * @hykOilPrice:油价信息
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/oilPrice")
    @ResponseBody
    public String oilPriceByCity(@RequestParam(value = "prov", defaultValue = "浙江", required = false) String prov) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            //prov=new String(prov.getBytes("ISO8859_1"), "UTF-8");
            HykOilPrice hykOilPrice = hykOilPriceService.getByCity(prov);
            if (!StringUtils.isEmpty(hykOilPrice)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                hykOilPrice.setCreateDateStr(dateFormat.format(new Date()));
            }
            map.put("code", "200");
            map.put("msg", "success");
            map.put("hykOilPrice", hykOilPrice);
        } catch (Exception e) {
            logger.info("获取该省油价信息异常");
            map.put("code", "400");
            map.put("msg", "获取该省油价信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述:  所有省份
     *
     * @auther: 霍中曦
     * @param:null
     * @list:所有省份
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/allProv")
    @ResponseBody
    public String allProv() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List list = hykOilPriceService.getAllCityName();
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            logger.info("获取该省油价信息异常");
            map.put("code", "400");
            map.put("msg", "获取该省油价信息异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:  所有精彩活动包含已过期
     *
     * @auther: 霍中曦
     * @param:null
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/wondefulImgAll")
    @ResponseBody
    public String indexWondefulImgAll(@RequestParam(value = "currPage", defaultValue = "1", required = false) Integer currPage) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykAd> list = hykAdService.wondefulImgAll(currPage, pageSize);
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            logger.info("获取所有精彩活动异常");
            map.put("code", "400");
            map.put("msg", "获取所有精彩活动异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述:  可用精彩活动
     *
     * @auther: 霍中曦
     * @param:null
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/wondefulImg")
    @ResponseBody
    public String indexWondefulImg() {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykAd> list = hykAdService.wondefulImg();
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            logger.info("获取可用精彩活动异常");
            map.put("code", "400");
            map.put("msg", "获取可用精彩活动异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

    /**
     * 功能描述:  行业资讯
     *
     * @auther: 霍中曦
     * @param:currPage 当前页
     * @return:list
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/allNews")
    @ResponseBody
    public String allNews(@RequestParam(value = "currPage", defaultValue = "1", required = false) Integer currPage) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            List<HykNews> list = hykNewsService.getAllNews(currPage, pageSize);
//            if (list.size() > 0 && !StringUtils.isEmpty(number)) {, Integer number
//                list=list.subList(0,1);
//            }
            for (HykNews hykNews : list) {
                String content = HtmlUtils.htmlUnescape(hykNews.getContent());//富文本转译
                hykNews.setContent(content);
            }
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", list);
        } catch (Exception e) {
            logger.info("获取行业资讯异常");
            map.put("code", "400");
            map.put("msg", "获取行业资讯异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述:  行业资讯
     *
     * @auther: 霍中曦
     * @param:id 新闻id
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "find/getOneNews")
    @ResponseBody
    public String getOneNews(@RequestParam(value = "id") String id) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykNews hykNews = hykNewsService.get(id);
            map.put("code", "200");
            map.put("msg", "success");
            map.put("list", hykNews);
        } catch (Exception e) {
            logger.info("获取行业资讯异常");
            map.put("code", "400");
            map.put("msg", "获取行业资讯异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
