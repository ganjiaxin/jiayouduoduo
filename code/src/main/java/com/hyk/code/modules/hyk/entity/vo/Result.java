package com.hyk.code.modules.hyk.entity.vo;

import com.google.gson.Gson;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.Map;

public class Result implements Serializable {
    /*参数不全*/
    public static final String CODE_400="400";
    /*对象为空*/
    public static final String CODE_401="401";
    /*类型不匹配*/
    public static final String CODE_402="402";
    /*参数不对*/
    public static final String CODE_403="403";
    /*请求结果成功*/
    public static final String CODE_200="200";
    /*请求结果失败*/
    public static final String CODE_404="404";

    /*请求异常*/
    public static final String CODE_501="501";


    @ApiModelProperty(value = "描述信息")
    private String msg;
    @ApiModelProperty(value = "返回编码")
    private String code;
    @ApiModelProperty(value = "查询接口的返回值")
    private Object data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public static String  info(String code, String msg, Result obj) {
        Gson gson=new Gson();
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(obj.getData());
        return gson.toJson(result);
    }


    public static String  info(String code, String msg, Map<String,Object> map) {
        Gson gson=new Gson();
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(map);
        return gson.toJson(result);
    }

    public static String info(String code, String msg) {
        Gson gson=new Gson();
        Result result=new Result();
        result.setCode(code);
        result.setMsg(msg);
        return gson.toJson(result);
    }
}
