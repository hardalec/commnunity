package com.jing.community.controller;

import com.jing.community.dto.CommentDto;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Comment;
import com.jing.community.entity.User;
import com.jing.community.service.CommentService;
import com.jing.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
        // 阅读数增加功能
        questionService.incView(id);
//        if(commentService.List(id) != null){
//            List<CommentDto> commentList  = commentService.List(id);
//            model.addAttribute("commentList", commentService.List(id));
//        }
//        List<CommentDto> commentList  = commentService.List(id);
        model.addAttribute("commentList", commentService.List(id));

        return "question";
    }
}
