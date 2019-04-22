/**
 * Copyright &copy; 2012-2016 <a href="http://www.uddd.com/">油大大</a> All rights reserved.
 */
package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.HykAdDao;
import com.hyk.code.modules.hyk.dao.HykNewsDao;
import com.hyk.code.modules.hyk.entity.HykAd;
import com.hyk.code.modules.hyk.entity.HykNews;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * 新闻管理Service
 *
 * @author 霍中曦
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class HykNewsService extends CrudService<HykNewsDao, HykNews> {
    @Autowired
    private HykNewsDao hykNewsDao;

    public List<HykNews> getAllNews(Integer currPage, Integer pageSize) {
        return hykNewsDao.getAllNews((currPage - 1) * pageSize, pageSize);
    };//所有可展示新闻

    public HykNews get(String id) {
        return hykNewsDao.get(id);
    };//get

}