package com.hyk.code.common.annotation;

import java.lang.annotation.*;

/**
 * 操作日志注解
 * Created by chzhxiang on 2018/4/12.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SystemLog {

    /**操作模块*/
    public static enum Module {
        USER("用户模块"),
        SETTING("系统设置");
        String value;

        Module(String value) {
            this.value = value;
        }
    }

    /**操作类型*/
    public static enum Operate {

        LOGIN(1,"登录"),
        QUERY(2,"查询"),
        INSERT(3,"新增"),
        UPDATE(4,"修改"),
        DELETE(5,"删除"),
        DOWNLOAD(6,"下载");

        Integer key;
        String value;

        Operate(Integer key,String value) {
            this.key = key;
            this.value = value;
        }

        public int getKey() {
            return key;
        }

        public String getValue() {
            return value;
        }

        public String getValueByKey(Integer key){
            for(Operate o : Operate.values()){
                if (o.key == key) {
                    return o.value;
                }
            }
            return null;
        }
    }

    Module module();            //模块名称

    Operate operateName();      //操作名称

    String value() default "";  //操作内容
}