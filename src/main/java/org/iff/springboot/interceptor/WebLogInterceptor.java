/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.AfterReturningAdvice;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * 登录拦截，用于 WEB 端
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Slf4j
public class WebLogInterceptor implements HandlerInterceptor, AfterReturningAdvice {

    private static String paramsToString(HttpServletRequest request) {
        Map<String, String[]> parameterMap = request.getParameterMap();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
            sb.append(entry.getKey()).append("=");
            String[] value = entry.getValue();
            if (value == null || value.length < 1) {
                sb.append("\n");
                continue;
            }
            if (value.length == 1) {
                sb.append(value[0]).append("\n");
                continue;
            }
            sb.append(StringUtils.join(value, ",")).append("\n");
        }
        if (sb.length() > 2000) {
            sb.setLength(20000);
            int lastIndexOf = sb.lastIndexOf("\n");
            if (lastIndexOf > 0) {
                sb.setLength(lastIndexOf);
            }
        }
        return sb.toString();
    }

    public static String remoteIp(HttpServletRequest request) {
        if (org.apache.commons.lang.StringUtils.isNotBlank(request.getHeader("X-Real-IP"))) {
            return request.getHeader("X-Real-IP");
        } else if (org.apache.commons.lang.StringUtils.isNotBlank(request.getHeader("X-Forwarded-For"))) {
            return request.getHeader("X-Forwarded-For");
        } else if (org.apache.commons.lang.StringUtils.isNotBlank(request.getHeader("Proxy-Client-IP"))) {
            return request.getHeader("Proxy-Client-IP");
        }
        return request.getRemoteAddr();
    }

    @Override
    public void afterReturning(Object arg0, Method arg1, Object[] arg2, Object arg3) {

    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
//        int index = request.getContextPath().length();
//        String uri = request.getRequestURI().substring(index);
//        String method = request.getMethod();
//        String ip = remoteIp(request);
//        HandlerMethod handlerMethod = (HandlerMethod) handler;
//        AccessLog al = handlerMethod.getMethodAnnotation(AccessLog.class);
//        if (al != null) {
//            AdminModule module = al.module();
//            String operation = al.operation();
//            log.info("module={},operation={},logParams={}", module, operation, al.logParams());
//            int dotIndex = uri.lastIndexOf(".");
//            String accessRuleUri = dotIndex > 0 ? uri.substring(0, dotIndex) : uri;
//            Admin admin = (Admin) request.getSession().getAttribute(SysConstant.SESSION_ADMIN);
//            long adminUid = admin == null ? -1 : admin.getId();
//            AdminAccessLog adminAccessLog = new AdminAccessLog();
//            adminAccessLog.setAdminId(adminUid);
//            adminAccessLog.setAccessIp(ip);
//            adminAccessLog.setAccessMethod(method);
//            adminAccessLog.setOperation(operation);
//            adminAccessLog.setModule(module);
//            adminAccessLog.setUri(accessRuleUri);
//            if (al.logParams()) {
//                //记录请求的参数列表
//                String params = paramsToString(request);
//                adminAccessLog.setOperationParams(params);
//            } else {
//                adminAccessLog.setOperationParams("");
//            }
//            //解决service为null无法注入问题
//            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//            AdminAccessLogService logService = (AdminAccessLogService) factory.getBean("adminAccessLogService");
//            logService.saveLog(adminAccessLog);
//        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub

    }
}
