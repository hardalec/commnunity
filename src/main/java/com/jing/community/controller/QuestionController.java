package com.jing.community.controller;

import com.jing.community.dto.CommentInfoDto;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.service.CommentService;
import com.jing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @GetMapping("/question/{id}")
    public String question(@PathVariable("id") Integer id, Model model, HttpServletRequest request){
        QuestionDto questionDto = questionService.getById(id);
        User user = (User) request.getSession().getAttribute("user");
        if(user.getAccountId() == questionDto.getUser().getAccountId()){
            model.addAttribute("showedit", 1);
        }
        model.addAttribute("questionDto", questionDto);
        // 文章标签
        String[] questionTags = questionService.getQuestionTags(id);
        model.addAttribute("questionTags", questionTags);
        // 根据标签关联的文章
        Set<Question> tagRelated = questionService.getRelatedQuestion(id);
        model.addAttribute("relatedQuestion", tagRelated);
        // 阅读数增加功能
        questionService.incView(id);
        List<CommentInfoDto> commentList = commentService.List(id, 1);
        model.addAttribute("commentList", commentList);


        return "question";
    }
}
