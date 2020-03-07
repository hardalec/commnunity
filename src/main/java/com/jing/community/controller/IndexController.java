package com.jing.community.controller;

import com.jing.community.service.QuestionService;
import com.jing.community.dto.PageInationDto;
import com.jing.community.entity.User;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                        Model model,
                        @RequestParam(value = "page", defaultValue = "1") Integer page,
                        @RequestParam(value = "size", defaultValue = "7") Integer size){
//        Cookie[] cookies = request.getCookies();
//        String token = null;
//        if(cookies != null && cookies.length != 0){
//            for (Cookie cookie : cookies) {
//                if (cookie.getName().equals("token")) {
//                    token = cookie.getValue();
//                    break;
//                }
//            }
//        }
//        if (token != null) {
//            User user = userRepository.findByToken(token);
//            request.getSession().setAttribute("user", user);
//        }

        // 获取首页所有文章列表
//        Pageable pageable = new PageRequest(page - 1, 6, Sort.Direction.ASC);
//        Page<questionService.list()> list = recordRepository.findAll(pageable);
        PageInationDto pagInations = questionService.list(page, size);

        model.addAttribute("pagInations", pagInations);
        return "index";
    }
}
