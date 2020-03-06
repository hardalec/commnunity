package com.jing.community.controller;

import com.jing.community.controller.service.QuestionService;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.User;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model){
        Cookie[] cookies = request.getCookies();
        String token = null;
        if(cookies != null && cookies.length != 0){
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }
        if (token != null) {
            User user = userRepository.findByToken(token);
            request.getSession().setAttribute("user", user);
        }

        // 获取首页所有文章列表
        List<QuestionDto> questionList = questionService.list();
        model.addAttribute("questions", questionList);
        return "index";
    }
}
