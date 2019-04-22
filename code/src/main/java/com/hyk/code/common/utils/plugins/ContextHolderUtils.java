package com.hyk.code.common.utils.plugins;
import org.springframework.web.context.request.*;
import javax.servlet.http.*;

public class ContextHolderUtils
{
    public static HttpServletRequest getRequest() {
        final HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        return request;
    }
    
    public static HttpSession getSession() {
        final HttpSession session = getRequest().getSession();
        return session;
    }
}
