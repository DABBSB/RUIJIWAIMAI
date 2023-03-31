//package org.DABB.filter;
//
//import com.alibaba.fastjson.JSON;
//import lombok.extern.slf4j.Slf4j;
//import org.DABB.commons.BaseContext;
//import org.DABB.commons.R;
//import org.springframework.util.AntPathMatcher;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@Slf4j
//@WebFilter(filterName = " LoginCheckFilter", urlPatterns = "/*")
//public class LoginCheckFilter2 implements Filter {
//    //    路径匹配器
//    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        HttpServletResponse response = (HttpServletResponse) servletResponse;
////        获取请求处理的URL
//        String requestURI = request.getRequestURI();
////获取不需要处理的URL
//        String[] urls = new String[]{
//                "/employee/login",
//                "/employee/logout",
//                "/backend/**",
//                "front/**"
//        };
//        boolean check = check(urls, requestURI);
//        log.info("询问询问{}询问询问", requestURI);
//        if (check) {
//            log.info("本次工作不需要处理");
//            filterChain.doFilter(request, response);
//            return;
//        }
////        判断是否为登陆状态
//        if (request.getSession().getAttribute("employee") != null) {
//            Object employee = request.getSession().getAttribute("employee");
//
//            BaseContext.setid((Long) employee);
//
//            log.info("用户已登陆");
//            filterChain.doFilter(request, response);
//            return;
//        }
//        log.info("用户未登录");
////        传输字节流到前端
//        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));
//    }
//
//    public boolean check(String[] urls, String requestURI) {
//        for (String url : urls) {
//            if (PATH_MATCHER.match(url, requestURI)) {
//                return true;
//            }
//        }
//        return false;
//    }
//}
