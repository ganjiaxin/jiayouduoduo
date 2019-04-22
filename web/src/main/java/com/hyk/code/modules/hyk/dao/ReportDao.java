package com.hyk.code.modules.hyk.dao;

import com.hyk.code.common.persistence.CrudDao;
import com.hyk.code.common.persistence.annotation.MyBatisDao;
import com.hyk.code.modules.hyk.entity.ReportIndex;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/18 14:19
 * @Description:
 */
@MyBatisDao
public interface ReportDao extends CrudDao<ReportIndex> {

     ReportIndex getYesterDayInifo();

    ReportIndex getYesterDayFindList(ReportIndex report);

}