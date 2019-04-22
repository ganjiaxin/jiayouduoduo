package com.hyk.code.modules.api;

import com.google.gson.Gson;
import com.hyk.code.common.token.TokenUtil;
import com.hyk.code.modules.hyk.entity.HykMessage;
import com.hyk.code.modules.hyk.entity.HykNotice;
import com.hyk.code.modules.hyk.entity.HykUser;
import com.hyk.code.modules.hyk.service.HykMessageService;
import com.hyk.code.modules.hyk.service.HykNoticeService;
import com.hyk.code.modules.hyk.service.HykRedpackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 功能描述:站内信内容  ios/android h5通用
 *
 * @auther: 霍中曦
 * @return:
 * @date: 2018/11/20 14:37
 */


@Controller
public class AppMessageController {
    private final static Logger logger = LoggerFactory.getLogger(AppMessageController.class);
    private final static Integer pageSize = 10;

    @Autowired
    private HykMessageService hykMessageService;
    @Autowired
    private HykNoticeService hykNoticeService;


    /**
     * 功能描述: 更新一个站内信为已读
     *
     * @auther: 霍中曦
     * @param:redType
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "msg/updateOne")
    @ResponseBody
    public String msgUpdateOne(HttpServletRequest request, @RequestParam(value = "token") String token, @RequestParam(value = "id") String id) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            hykMessageService.updateStatusById(id);
            map.put("code", "200");
            map.put("msg", "success");

        } catch (Exception e) {
            logger.info("更新站内信为已读异常");
            map.put("code", "400");
            map.put("msg", "更新站内信为已读异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 更新站内信为已读
     *
     * @auther: 霍中曦
     * @param:redType
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "msg/updateAll")
    @ResponseBody
    public String msgUpdateAll(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            if (hykUser.getId() == null || hykUser.getId().length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                hykMessageService.updateStatusByPhone(hykUser.getPhone());
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("更新全部站内信为已读异常");
            map.put("code", "400");
            map.put("msg", "更新全部站内信为已读异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 该用户所有站内信
     *
     * @auther: 霍中曦
     * @param:redType
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "msg/getAll")
    @ResponseBody
    public String msgGetAll(HttpServletRequest request, @RequestParam(value = "token") String token) {//,@RequestParam(value = "currPage",defaultValue = "1",required = false)Integer currPage
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            if (hykUser.getId() == null || hykUser.getId().length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {
                List list = hykMessageService.getAll(hykUser.getPhone());//,currPage,pageSize
                map.put("list", list);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取全部站内信异常");
            map.put("code", "400");
            map.put("msg", "获取全部站内信异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }


    /**
     * 功能描述: 最新站內信标题
     *
     * @auther: 霍中曦
     * @param:redType
     * @return:
     * @date: 2018/11/10
     */
    @RequestMapping(value = "msg/newTitle")
    @ResponseBody
    public String msgGetAl(HttpServletRequest request, @RequestParam(value = "token") String token) {
        Gson gson = new Gson();
        Map map = new HashMap();
        try {
            HykUser hykUser = TokenUtil.getAppCurrentUser(token);
            if (hykUser.getId() == null || hykUser.getId().length() <= 0) {
                map.put("code", "300");
                map.put("msg", "登录已失效 重新登录");
            } else {

                HykMessage hykMessage = hykMessageService.getNewTitle(hykUser.getPhone());
                Integer count = hykMessageService.getUnreadCount(hykUser.getPhone());
                String title = "";
                if (hykMessage != null) {
                    title = hykMessage.getTitle();
                }
                HykNotice hykNotice = hykNoticeService.findNewestTitle();
                map.put("hykNotice", hykNotice);
                map.put("title", title);
                map.put("count", count);
                map.put("code", "200");
                map.put("msg", "success");
            }
        } catch (Exception e) {
            logger.info("获取站内信标题异常");
            map.put("code", "400");
            map.put("msg", "获取站内信标题异常");
            e.printStackTrace();
        }
        return gson.toJson(map);
    }

}
