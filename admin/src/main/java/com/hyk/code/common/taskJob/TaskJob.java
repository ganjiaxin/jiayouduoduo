package com.hyk.code.common.taskJob;

import com.hyk.code.modules.hyk.service.HykMonthReportService;
import com.hyk.code.modules.hyk.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class TaskJob {

	@Autowired
	private ReportService reportService;

	@Autowired
	private HykMonthReportService hykMonthReportService;


	/**
	 * 功能描述: 每个月月初
	 * @auther: 霍中曦
	 * @date: 2019/1/23 16:39
	 */
	@Scheduled(cron = "0 0 1 1 * ?")//每月第一天 凌晨一点
	public void getToken_ac() {



	}


}

