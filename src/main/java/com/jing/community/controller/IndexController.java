package com.jing.community.controller;

import com.jing.community.entity.UserEntity;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        String token = "";
        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("token")) {
                token = cookie.getValue();
                break;
            }
        }
        if (token != null) {
            UserEntity userEntity = userRepository.findByToken(token);
            request.getSession().setAttribute("user", userEntity);
        }
        return "index";
    }
}
