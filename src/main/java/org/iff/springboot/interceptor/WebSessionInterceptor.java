/*******************************************************************************
 * Copyright (c) 2019-10-29 @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>.
 * All rights reserved.
 *
 * Contributors:
 *     <a href="mailto:iffiff1@gmail.com">Tyler Chen</a> - initial API and implementation.
 ******************************************************************************/
package org.iff.springboot.interceptor;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 会话拦截器，用于 WEB 端
 *
 * @author <a href="mailto:iffiff1@gmail.com">Tyler Chen</a>
 * @since 2019-10-29
 * auto generate by qdp.
 */
@Slf4j
public class WebSessionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
//        AdminService adminService = (AdminService) factory.getBean("adminService");
//        logger.info(request.getContextPath());
//        Subject currentUser = SecurityUtils.getSubject();
//
//        //判断用户是通过记住我功能自动登录,此时session失效
//        if (!currentUser.isAuthenticated() && currentUser.isRemembered()) {
//            try {
//                Admin admin = adminService.findByUsername(currentUser.getPrincipals().toString());
//                //对密码进行加密后验证
//                UsernamePasswordToken token = new UsernamePasswordToken(admin.getUsername(), admin.getPassword(), currentUser.isRemembered());
//                //把当前用户放入session
//                currentUser.login(token);
//                Session session = currentUser.getSession();
//                session.setAttribute(SysConstant.SESSION_ADMIN, admin);
//                //设置会话的过期时间--ms,默认是30分钟，设置负数表示永不过期
//                session.setTimeout(30 * 60 * 1000L);
//            } catch (Exception e) {
//                //自动登录失败,跳转到登录页面
//                //response.sendRedirect(request.getContextPath()+"/system/employee/sign/in");
//                ajaxReturn(response, 4000, "unauthorized");
//                return false;
//            }
//            if (!currentUser.isAuthenticated()) {
//                //自动登录失败,跳转到登录页面
//                ajaxReturn(response, 4000, "unauthorized");
//                return false;
//            }
//        }
        return true;
    }

    public void ajaxReturn(HttpServletResponse response, int code, String msg) throws Exception {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/json; charset=UTF-8");
        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("message", msg);
        out.print(json.toString());
        out.flush();
        out.close();
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
