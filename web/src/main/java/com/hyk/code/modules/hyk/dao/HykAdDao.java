/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户红包管理DAO接口
 * @author 霍中曦
 * @version 2018-11-12
 */
@MyBatisDao
public interface HykAdDao extends CrudDao<HykAd> {
    /**
     * 获取banner中的所有
     * @return
     */
    @Select("SELECT banner_img as bannerImg, title as title,url as url,app_img as appImg,share_img as shareImg,shareTitle as shareTitle,shareSonTitle as shareSonTitle,shareUrl as shareUrl from hyk_ad where  del_flag=0 and status=1 and " +
            " LENGTH(banner_img)>0 and NOW() BETWEEN start_time and end_time ORDER BY sort,end_time")
    List<HykAd> All();


    @Select("SELECT * , case when (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(end_time))>=0 then 1 else 0 end as overSign from hyk_ad where LENGTH(wondeful_img)>0 and UNIX_TIMESTAMP(NOW())>UNIX_TIMESTAMP(end_time) " +
            "and del_flag=0 and status=1 ORDER BY  sort, end_time")
    List<HykAd> wondefulImgEnd();//所有已过期精彩活动
    @Select("SELECT *,case when (UNIX_TIMESTAMP(NOW())-UNIX_TIMESTAMP(end_time))>=0 then 1 else 0 end as overSign from hyk_ad " +
            "where LENGTH(wondeful_img)>0 and del_flag=0 and status=1 and NOW()BETWEEN start_time and end_time ORDER BY sort,end_time")
    List<HykAd> wondefulImg();//所有未过期精彩活动

    @Update("update hyk_ad set status=0 where UNIX_TIMESTAMP(NOW())>=UNIX_TIMESTAMP(end_time) and status =1")
    Integer updateStaus();

    HykAd getHykAdAppImg();//app广告弹窗
    List<HykAd> getHykAdShareImg();//分享
    HykAd getHykAdOpenImg();//开屏广告
    List<HykAd> goodsImg();//商城bannner
	
}