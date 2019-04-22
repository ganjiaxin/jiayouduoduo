package com.hyk.code.modules.hyk.service;

import com.hyk.code.common.persistence.Page;
import com.hyk.code.common.service.CrudService;
import com.hyk.code.common.utils.DateUtil;
import com.hyk.code.modules.hyk.dao.HykAddressDao;
import com.hyk.code.modules.hyk.dao.HykHolidaysDao;
import com.hyk.code.modules.hyk.entity.HykAddress;
import com.hyk.code.modules.hyk.entity.HykHolidays;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Description: 用户地址管理Service
 * @Author: 甘佳欣
 * @Date: 2019/4/19
 */
@Service
@Transactional(readOnly = true)
public class HykHolidaysService extends CrudService<HykHolidaysDao, HykHolidays> {
    @Autowired
    private HykHolidaysDao hykHolidaysDao;

    public HykHolidays get(String id) {
        return super.get(id);
    }

    public List<HykHolidays> findList(HykHolidays hykHolidays) {
        return super.findList(hykHolidays);
    }

    public Page<HykHolidays> findPage(Page<HykHolidays> page, HykHolidays hykHolidays) {
        return super.findPage(page, hykHolidays);
    }

    @Transactional(readOnly = false)
    public void save(HykHolidays hykHolidays) {
        super.save(hykHolidays);
    }

    @Transactional(readOnly = false)
    public void delete(HykHolidays hykHolidays) {
        super.delete(hykHolidays);
    }

    public Date getHolidaysByDate(Date date) {
        HykHolidays hykHolidays = hykHolidaysDao.getHolidaysByDate(date);
        String code = hykHolidays.getType();
        Calendar cal= Calendar.getInstance();
        cal.setTime(date);//设置起时间
        for (int i = 0; "1".equals(code) || "2".equals(code); i++) {
            cal.add(Calendar.DATE, 1);//增加一天  
            hykHolidays = hykHolidaysDao.getHolidaysByDate(cal.getTime());
            code=hykHolidays.getType();
            System.out.println(cal);
            System.out.println(code);

        }
        return cal.getTime();
    }

    ;

    /**
     * 定时任务
     *
     * @return
     */
    @Transactional(readOnly = false, rollbackFor = {Exception.class})
    public Integer holidaysTask() {
        try {
            List<HykHolidays> list = new ArrayList<>();
            Calendar c = Calendar.getInstance();
            for (int i = 0; i <= 1100; i++) {
                HykHolidays hykHolidays = new HykHolidays();
                hykHolidays.setCreateDate(new Date());
                c.add(Calendar.DAY_OF_MONTH, 1);
                String code = DateUtil.holidaysCode(c.getTime());
                hykHolidays.setType(code);
                hykHolidays.setCalendar(c.getTime());
                list.add(hykHolidays);
            }
            return hykHolidaysDao.insertHolidaysList(list);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 0;
        }
    }
}