package com.jing.community.controller;

import com.jing.community.cache.TagCache;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import com.jing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
public class PublishController {
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionService questionService;
    @GetMapping("/publish")
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
        return "publish";
    }
    @PostMapping("/publish")
    public String doPublic(@RequestParam(value = "title", required = false) String title,
                           @RequestParam(value = "description", required = false) String description,
                           @RequestParam(value = "tag", required = false) String tag,
                           @RequestParam(value = "id", required = false) Integer id,
                           HttpServletRequest request,
                           Model model){
        // 获取输入信息进行回显
        model.addAttribute("tags", TagCache.get());
        model.addAttribute("id", id);
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

        User user = (User) request.getSession().getAttribute("user");
        Question question;
        if (user == null) {
            model.addAttribute("error", "用户未登录");
            return "publish";
        }else if(id == null || questionRepository.findById(id) == null) {
            question = new Question();
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setViewCnt(0); question.setCommentCnt(0);question.setLikeCnt(0);
            question.setCreater(user.getId());
        }else {
            question = questionRepository.findById(id).get();
            question.setGmtModified(System.currentTimeMillis());
        }
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
            // 保存信息，刷新返回首页
        questionRepository.save(question);
        return "redirect:/";
    }


    @GetMapping("/publish/{id}")
    public String editPublish(@PathVariable("id") Integer questionId, Model model){
        QuestionDto questionDto = questionService.getById(questionId);

        model.addAttribute("tags", TagCache.get());
        model.addAttribute("id", questionId);
        model.addAttribute("title", questionDto.getTitle());
        model.addAttribute("description",questionDto.getDescription());
        model.addAttribute("tag", questionDto.getTag());
        return "publish";
    }
}
