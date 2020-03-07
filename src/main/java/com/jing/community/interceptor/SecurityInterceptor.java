package com.jing.community.interceptor;

import com.jing.community.entity.User;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@Service
public class SecurityInterceptor implements HandlerInterceptor {
    // 没有service 注解的话，Autowired 不工作，因此会产生空指针， 原因是继承的接口替代了bean进行了注入
    @Autowired
    private UserRepository userRepository;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 尝试直接从session中拿，
//        Object user = request.getSession().getAttribute("user");
//        if(user != null){
//            return true;
//        }
        // cookies 的方法，可以保持登录
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userRepository.findByToken(token);
                    if(user != null){
                        request.getSession().setAttribute("user", user);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
