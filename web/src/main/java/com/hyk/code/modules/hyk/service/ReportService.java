package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.service.CrudService;
import com.hyk.code.modules.hyk.dao.ReportDao;
import com.hyk.code.modules.hyk.entity.ReportIndex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/18 14:18
 * @Description:
 */


@Service
@Transactional(readOnly = true)
public class ReportService extends CrudService<ReportDao, ReportIndex> {
    @Autowired
    private ReportDao reportDao;

    public ReportIndex getYesterDayInifo() {
        return reportDao.getYesterDayInifo();
    }
    public ReportIndex getYesterDayFindList(ReportIndex report) {
        return reportDao.getYesterDayFindList(report);
    }

}