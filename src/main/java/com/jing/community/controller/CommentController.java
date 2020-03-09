package com.jing.community.controller;

import com.jing.community.dto.CommentCreateDto;
import com.jing.community.entity.Comment;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.CommentRepository;
import com.jing.community.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

//@Controller
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionRepository questionRepository;

//    @ResponseBody
//    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request){
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        User user = (User) request.getSession().getAttribute("user");
        comment.setCommentor(user.getName());
        commentRepository.save(comment);
        Question question = questionRepository.findById(commentCreateDto.getParentId()).get();
        question.setCommentCnt(question.getCommentCnt() + 1);
        questionRepository.save(question);
        HashMap<Object, Object> map = new HashMap<>();
        map.put("message","发布成功");
        return map;
    }
}
