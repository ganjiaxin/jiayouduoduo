package com.hyk.code.common.utils.plugins;

import java.text.*;
import java.util.*;

public class SimpleFormat
{
    public String number(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? 0 : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat("0.00").format(Double.parseDouble(obj.toString()));
    }
    
    public String number(Object obj, final String pattern) {
        obj = ((obj == null || obj.toString().length() == 0) ? 0 : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat(pattern).format(Double.parseDouble(obj.toString()));
    }
    
    public String round(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? 0 : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "NaN";
        }
        return new DecimalFormat("0").format(Double.parseDouble(obj.toString()));
    }
    
    public String currency(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? 0 : obj);
        return NumberFormat.getCurrencyInstance(Locale.CHINA).format(obj);
    }
    
    public String timestampToString(final Object obj, final String pattern) {
        if (obj == null) {
            return "";
        }
        final DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        final DateFormat format2 = new SimpleDateFormat(pattern);
        Date date = null;
        try {
            date = format1.parse(obj.toString());
        }
        catch (ParseException e) {
            e.printStackTrace();
            return "error";
        }
        return format2.format(date);
    }
    
    public String percent(Object obj) {
        obj = ((obj == null || obj.toString().length() == 0) ? 0 : obj);
        if (obj.toString().equalsIgnoreCase("NaN")) {
            return "";
        }
        return NumberFormat.getPercentInstance(Locale.CHINA).format(obj);
    }
    
    public String date(final Object obj, final String pattern) {
        if (obj == null) {
            return "";
        }
        return new SimpleDateFormat(pattern).format(obj);
    }
    
    public String date(final Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getDateInstance(1, Locale.CHINA).format(obj);
    }
    
    public String time(final Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getTimeInstance(3, Locale.CHINA).format(obj);
    }
    
    public String datetime(final Object obj) {
        if (obj == null) {
            return "";
        }
        return DateFormat.getDateTimeInstance(1, 3, Locale.CHINA).format(obj);
    }
}
