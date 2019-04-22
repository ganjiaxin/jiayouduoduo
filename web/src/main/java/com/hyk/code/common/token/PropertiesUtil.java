package com.hyk.code.common.token;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Auther: 霍中曦
 * @Date: 2018/11/9 10:27
 * @Description:
 */
public class PropertiesUtil {
    public static Properties props = new Properties();
    public static void load(String path) {
        InputStream in = PropertiesUtil.class.getClassLoader()
                .getResourceAsStream(path);
        try {
            props.load(in);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getValue(String key) {
        // 根据指定key值获取value
        load("application.properties");
        return props.getProperty(key);
    }
}
