package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.utils.StringUtils;
import com.hyk.code.common.web.BaseController;
import com.hyk.code.modules.hyk.entity.HykNews;
import com.hyk.code.modules.hyk.service.HykNewsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Auther: 霍中曦
 * @Date: 2019/3/7 10:18
 * @Description:
 */
@Api(description = "新闻管理")
@Controller
@RequestMapping(value = "${frontPath}/web/")
public class WebHykNewsController extends BaseController{

        @Autowired
        private HykNewsService hykNewsService;

        @ModelAttribute
        public HykNews get(@RequestParam(required = false) String id) {
            HykNews entity = null;
            if (StringUtils.isNotBlank(id)) {
                entity = hykNewsService.get(id);
            }
            if (entity == null) {
                entity = new HykNews();
            }
            return entity;
        }

        @RequestMapping(value ="hykNews")
        @ResponseBody
        @ApiOperation(httpMethod = "POST", value = "新闻管理")
        public String list(@RequestParam("callback") String callback,HykNews hykNews,HttpServletRequest request, HttpServletResponse response) {
            Gson gson=new Gson();
            Map<String,Object> map=new HashMap<String,Object>();
            map.put("code","200");
            map.put("msg","新闻管理请求成功");
            map.put("permission",request.getAttribute("permission"));
            Page<HykNews> page = hykNewsService.findPage(new Page<HykNews>(request, response), hykNews);
            map.put("page", page);
            return  callback+"("+gson.toJson(map)+")";
        }


        @RequestMapping(value = "hykNews/save")
        @ResponseBody
        @ApiOperation(httpMethod = "POST", value = "站内信管理")
        public String save(@RequestParam("callback") String callback,HykNews hykNews) {
            Gson gson=new Gson();
            Map<String,String> map=new HashMap<String,String>();
            try {
                map.put("code", "200");
                map.put("msg", "保存新闻管理成功");
                hykNews.setCreateDate(new Date());
                hykNewsService.save(hykNews);
            }catch (Exception e){
                e.printStackTrace();
                map.put("code", "400");
                map.put("msg", "保存新闻管理失败");
            }
            return  callback+"("+gson.toJson(map)+")";
        }

        @RequestMapping(value = "hykNews/delete")
        @ResponseBody
        @ApiOperation(httpMethod = "POST", value = "删除新闻")
        public String delete(@RequestParam("callback") String callback,@RequestParam("id") String id){
            Gson gson=new Gson();
            Map<String,String> map=new HashMap<String,String>();
            try {
                map.put("code", "200");
                map.put("msg", "删除新闻成功");
                HykNews hykNews=new HykNews();
                hykNews.setId(id);
                hykNewsService.delete(hykNews);
            }catch (Exception e){
                e.printStackTrace();
                map.put("code", "400");
                map.put("msg", "删除新闻失败");
            }
            return  callback+"("+gson.toJson(map)+")";
        }

    @RequestMapping(value = "hykNews/getId")
    @ResponseBody
    @ApiOperation(httpMethod = "POST", value = "单条记录请求")
    public String getId(@RequestParam("callback") String callback,@RequestParam("id") String id) {
        Gson gson=new Gson();
        Map<String,Object> map=new HashMap<String,Object>();
        try {
            map.put("code", "200");
            map.put("msg", "新闻请求成功");
            HykNews hykNews = hykNewsService.get(id);
            map.put("hykNews",hykNews);
        }catch (Exception e){
            e.printStackTrace();
            map.put("code", "400");
            map.put("msg", "新闻请求失败");
        }
        return  callback+"("+gson.toJson(map)+")";
    }
}
