package com.example.filter;

import com.alibaba.fastjson.JSON;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import com.example.common.BaseContext;
import com.example.common.Result;
import org.springframework.util.AntPathMatcher;

import java.io.IOException;

/**
 * @author Yushun Shao
 * @description: login check filter
 */
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {

    //路径匹配器
    public  static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        //1.获取本次请求的uri
        String requestURI = request.getRequestURI();

//        log.info("拦截到请求：{}",requestURI);

        //定义不需要处理的请求路径
        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/user/register"
        };
        //2.判断请求是否需要处理
        boolean check = check(urls, requestURI);

        //3.不需要处理则放行
        if (check){
//            log.info("本次请求{}不需要处理",requestURI);
            filterChain.doFilter(request,response);
            return;
        }

        //4-1.判断员工登录状态 已登录则放行
        if (request.getSession().getAttribute("employee") != null){
//            log.info("本次请求{}已登录",requestURI);
            String empId = (String) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);
            filterChain.doFilter(request,response);
            return;
        }

        //4-2.判断用户登录状态 已登录则放行
        if (request.getSession().getAttribute("user") != null){
//            log.info("本次请求{}已登录",requestURI);
            String userId = (String) request.getSession().getAttribute("user");
            BaseContext.setCurrentId(userId);
            filterChain.doFilter(request,response);
            return;
        }

        //5.未登录则返回未登录结果 通过输出流向客户端响应数据 /backend/js/request.js 响应拦截器
//        log.info("本次请求{}未登录",requestURI);
        response.getWriter().write(JSON.toJSONString(Result.error("NOTLOGIN")));

    }

    /**
     * 路径匹配 检查是否需要放行
     * @param urls
     * @param requestURI
     * @return
     */
    public boolean check(String[] urls,String requestURI){
        for (String url : urls){
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }
}
