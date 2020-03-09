package com.jing.community.controller;

import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class PublishController {

    @Autowired
    QuestionRepository questionRepository;

    @Autowired
    UserRepository userRepository;

    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @PostMapping("/publish")
    public String doPublic(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "tag", required = false) String tag,
                           HttpServletRequest request,
                           Model model){
        // 获取输入信息进行回显
        model.addAttribute("title", title);
        model.addAttribute("description", description);
        model.addAttribute("tag", tag);
        if(title == "" || title == null){
            model.addAttribute("error", "标题不能为空");
            return "publish";
        }
        if(description == "" || description == null){
            model.addAttribute("error", "问题描述不能为空");
            return "publish";
        }
        if(tag == "" || tag == null){
            model.addAttribute("error", "标签不能为空");
            return "publish";
        }
        // 获取登录用户信息
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
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }else{
//            User user = userRepository.findByToken(token);
            // 将用户信息存入question
            Question question = new Question();
            question.setTitle(title);
            question.setDescription(description);
            question.setTag(tag);
            question.setCreate(user.getId());
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCnt(0); question.setCommentCnt(0);question.setLikeCnt(0);
            // 保存信息，刷新返回首页
            questionRepository.save(question);
            return "redirect:/";
        }
    }

    @PutMapping("/publish/{id}")
    public String editPublish(@PathVariable("questionId") Integer questionId){
//        Optional<Question> question = questionRepository.findById(questionId);
//
        return "publish";
    }
}
