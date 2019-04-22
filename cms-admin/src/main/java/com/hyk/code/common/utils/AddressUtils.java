package com.hyk.code.common.utils;

import java.util.HashMap;
import java.util.Map;

public class AddressUtils {

	public static String getJancheng(String province) {
		
		Map<String,String> map=new HashMap<String,String>();
		map.put("北京市", "京");
		map.put("天津市", "津");
		map.put("河北省", "冀");
		map.put("山西省", "晋");
		map.put("内蒙古自治区", "蒙");
		map.put("辽宁省", "辽");
		map.put("吉林省", "吉");
		map.put("黑龙江省", "黑");
		map.put("上海市", "沪");
		map.put("江苏省", "苏");
		map.put("浙江省", "浙");
		map.put("安徽省", "皖");
		map.put("福建省", "闽");
		map.put("江西省", "赣");
		map.put("山东省", "鲁");
		map.put("河南省", "豫");
		map.put("湖北省", "鄂");
		map.put("湖南省", "湘");
		map.put("广东省", "粤");
		map.put("广西壮族自治区", "桂");
		map.put("海南省", "琼");
		map.put("四川省", "川");
		map.put("贵州省", "贵");
		map.put("云南省", "云");
		map.put("重庆市", "渝");
		map.put("西藏自治区", "藏");
		map.put("陕西省", "陕");
		map.put("甘肃省", "甘");
		map.put("青海省", "青");
		map.put("宁夏回族自治区", "宁");
		map.put("新疆维吾尔自治区", "新");
		map.put("香港特别行政区", "港");
		map.put("台湾省", "台");
		map.put("澳门特别行政区", "澳");
		
		Map<String,String> map1=new HashMap<String,String>();
		map1.put("北京市", "1");
		map1.put("天津市", "2");
		map1.put("河北省", "3");
		map1.put("山西省", "4");
		map1.put("内蒙古自治区", "5");
		map1.put("辽宁省", "6");
		map1.put("吉林省", "7");
		map1.put("黑龙江省", "8");
		map1.put("上海市", "9");
		map1.put("江苏省", "10");
		map1.put("浙江省", "11");
		map1.put("安徽省", "12");
		map1.put("福建省", "13");
		map1.put("江西省", "14");
		map1.put("山东省", "15");
		map1.put("河南省", "16");
		map1.put("湖北省", "17");
		map1.put("湖南省", "18");
		map1.put("广东省", "19");
		map1.put("广西壮族自治区", "20");
		map1.put("海南省", "21");
		map1.put("四川省", "22");
		map1.put("贵州省", "23");
		map1.put("云南省", "24");
		map1.put("重庆市", "25");
		map1.put("西藏自治区", "26");
		map1.put("陕西省", "27");
		map1.put("甘肃省", "28");
		map1.put("青海省", "29");
		map1.put("宁夏回族自治区", "30");
		map1.put("新疆维吾尔自治区", "31");
		map1.put("香港特别行政区", "32");
		map1.put("台湾省", "33");
		map1.put("澳门特别行政区", "34");
		return map1.get(province);
		
	}
}
