/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules;

import com.hyk.code.common.persistence.DataEntity;

/**
 * 地址管理Entity
 *
 * @author 霍中曦
 * @version 2018-11-09
 */
public class HykUserImg extends DataEntity<HykUserImg> {

    private static final long serialVersionUID = 1L;
    private String userId;        // 用户编号
    private String adId;        // 广告id

    public HykUserImg() {
        super();
    }

    public HykUserImg(String id) {
        super(id);
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }

}