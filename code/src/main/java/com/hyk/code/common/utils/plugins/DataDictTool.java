package com.hyk.code.common.utils.plugins;

import java.math.*;
import org.apache.commons.lang.*;

public class DataDictTool
{
   // private static SystemActTxtService systemActTxtService;
    
    public int getProductRateNum(final int num, final int rate) {
        int count = 0;
        count = num * rate;
        return count;
    }
    
    public boolean isLteZero(final BigDecimal productNewPrice) {
        boolean flag = false;
        if (productNewPrice != null && productNewPrice.compareTo(BigDecimal.ZERO) <= 0) {
            flag = true;
        }
        return flag;
    }
    
//    public String subText(final String text, final int length, final String endWith) {
//        return CommonUtil.subString(text, length, endWith);
//    }
    
    public String priceNoZero(final BigDecimal price) {
        String priceStr = "";
        if (price != null) {
            priceStr = price.toString();
            priceStr = priceStr.replaceAll("0+$", "");
            priceStr = priceStr.replaceAll("[.]$", "");
        }
        return priceStr;
    }
    
    public String getSysText(final String code, final String actCode) {
        final String text ="";// DataDictTool.systemActTxtService.queryActTxtByCode(code, actCode);
        if (StringUtils.isEmpty(text)) {
            return "";
        }
        return text;
    }
    
    public String escapeHtml(String html) {
        try {
            html = StringEscapeUtils.escapeHtml(html);
        }
        catch (Exception e) {
            return "";
        }
        return html;
    }
    
    public String unescapeHtml(String html) {
        try {
            html = StringEscapeUtils.unescapeHtml(html);
        }
        catch (Exception e) {
            return "";
        }
        return html;
    }
//    
//    public static void setSystemActTxtService(final SystemActTxtService systemActTxtService) {
//        DataDictTool.systemActTxtService = systemActTxtService;
//    }
//    
    public static void main(final String[] args) {
        final DataDictTool tool = new DataDictTool();
        String html = "<div name=\"searchColums\"></div>";
        html = tool.escapeHtml(html);
        System.out.println(html);
        html = tool.unescapeHtml(html);
        System.out.println(html);
    }
}
