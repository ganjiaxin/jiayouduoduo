package com.hyk.code.common.taskJob;/*
package com.ykds.code.common.taskJob;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ykds.code.common.utils.wechatUtils;
import com.ykds.code.modules.carwxappidinfo.entity.CarWxappidinfo;
import com.ykds.code.modules.carwxappidinfo.service.CarWxappidinfoService;
import com.ykds.code.modules.carwxinfo.entity.CarWxinfo;
import com.ykds.code.modules.carwxinfo.service.CarWxinfoService;


@Component
public class TaskJob {

	*/
/**
	* @Title: getToken_ac 
	* @Description: TODO 定时获取 access_token
	* @date 2017年9月1日 上午10:18:11  
	* @author  wangyw
	*//*

	@Scheduled(cron = "0 0 *//*
1 * * ?")
	//@Scheduled(cron = "0 *//*
5 * * * ?")
	public void getToken_ac() {

		SimpleDateFormat  sdf=new SimpleDateFormat("yyyyMMddHHmmss");
		System.out.println(wechatUtils.develeper+"=======定时获取token========="+sdf.format(new Date()));

		CarWxinfo carWxinfo=new CarWxinfo();
		List<CarWxinfo> list=carWxinfoService.findList(carWxinfo);
			 if(list.size()>0&&!wechatUtils.develeper) {
				 carWxinfo=list.get(0);

				 String  component_access_token=wechatUtils.get_component_access_token(carWxinfo.getComponentVerifyTicket());
				 carWxinfo.setComponentAccessToken(component_access_token);

				 CarWxappidinfo carWxappidinfo = new CarWxappidinfo();
				 List<CarWxappidinfo> wxappilist=carWxappidinfoService.findList(carWxappidinfo);
				 for(int i=0;i<wxappilist.size();i++) {
					 System.out.println("getAuthorizerAppid=="+wxappilist.get(i).getAuthorizerAppid());
					 System.out.println("getAuthorizerRefreshToken=="+wxappilist.get(i).getAuthorizerRefreshToken());
					 System.out.println("getComponentAccessToken=="+carWxinfo.getComponentAccessToken());
					 Map<String, Object> map=wechatUtils.api_authorizer_token(wxappilist.get(i).getAuthorizerAppid(),wxappilist.get(i).getAuthorizerRefreshToken(),carWxinfo.getComponentAccessToken());
					 wxappilist.get(i).setAuthorizerAccessToken(map.get("authorizer_access_token")+"");
					 wxappilist.get(i).setAuthorizerRefreshToken(map.get("authorizer_refresh_token")+"");

					 String jsapi_ticket=wechatUtils.getJsapiTicket(wxappilist.get(i).getAuthorizerAccessToken()).get("ticket")+"";
					 wxappilist.get(i).setJsapiTicket(jsapi_ticket);
					 System.out.println("getAuthorizerAccessToken=="+wxappilist.get(i).getAuthorizerAccessToken());
					 carWxappidinfoService.save(wxappilist.get(i));
				 }

				 carWxinfoService.save(carWxinfo);
			 }
	}

}
*/
