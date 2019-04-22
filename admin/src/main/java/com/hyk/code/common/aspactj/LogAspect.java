package com.hyk.code.common.aspactj;

import com.alibaba.fastjson.JSONObject;
import com.hyk.code.common.UserOperateLog;
import com.hyk.code.common.annotation.SystemLog;
import com.hyk.code.common.utils.RequestIpAddress;
import com.hyk.code.common.utils.UserAgentUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * 日志拦截器
 * Created by chzhxiang on 2018/4/12.
 */
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 保存系统操作日志
     * @param joinPoint 连接点
     * @return 方法执行结果
     * @throws Throwable 调用出错
     */
    @Around(value = "@annotation(com.hyk.code.common.annotation.SystemLog)")
    public Object saveLog(ProceedingJoinPoint joinPoint) throws Throwable {

        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpSession session = request.getSession();

        Method method = getMethod(joinPoint);                                   //获取设置SystemLog注释的方法
        SystemLog systemLogAnnotation = method.getAnnotation(SystemLog.class);  //获取注释信息
        SystemLog.Module module = systemLogAnnotation.module();                 //获取模块名称
        SystemLog.Operate operate = systemLogAnnotation.operateName();          //获取操作名称
        String description = systemLogAnnotation.value();                       //解析描述
        String ipAddress = RequestIpAddress.getIpAddress(request);

        try {
            request.setCharacterEncoding("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        JSONObject paramsJSON = new JSONObject();
        Map<String, Object> reqParamMap = new HashMap<String, Object>();
        reqParamMap.putAll(request.getParameterMap());
        for (String key : reqParamMap.keySet()) {
            if(key.equals("password") || key.equals("trans_pwd")){
                paramsJSON.put(key, "******");
            }else{
                paramsJSON.put(key, request.getParameter(key));
            }
        }


        UserOperateLog userOperateLog = new UserOperateLog();
        userOperateLog.setDescription(description);
        userOperateLog.setOperateIp(ipAddress);
        userOperateLog.setModule(module.name());
        userOperateLog.setOperateType(operate.getKey());
        userOperateLog.setUrl(request.getRequestURI());
        userOperateLog.setMethod(method.getName());
        if(paramsJSON != null){
            userOperateLog.setParams(JSONObject.toJSONString(paramsJSON));
        }



        Object object= joinPoint.proceed();

        doSaveLog(userOperateLog);

        return object;
    }



    /**
     * 获取当前执行的方法
     * @param joinPoint 连接点
     * @return 方法
     */
    private Method getMethod(ProceedingJoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Method[] methods = joinPoint.getTarget().getClass().getMethods();
        Method resultMethod = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                resultMethod = method;
                break;
            }
        }
        return resultMethod;
    }



    /**队列保存行为日志*/
    public void doSaveLog(UserOperateLog userOperateLog){

        try {
            if(userOperateLog != null){
                System.out.println("==========="+userOperateLog.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}


