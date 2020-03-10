package com.jing.community.controller;

import com.jing.community.dto.CommentCreateDto;
import com.jing.community.dto.CommentInfoDto;
import com.jing.community.entity.Question;
import com.jing.community.repository.CommentRepository;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

//@Controller
@RestController
public class CommentController {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CommentService commentService;

//    @ResponseBody
//    @RequestMapping(value = "/comment", method = RequestMethod.POST)
    @PostMapping("/comment")
    public Object post(@RequestBody CommentCreateDto commentCreateDto, HttpServletRequest request){
        commentService.setComment(commentCreateDto, request);
        if(commentCreateDto.getType() == 1){
            Question question = questionRepository.findById(commentCreateDto.getParentId()).get();
            question.setCommentCnt(question.getCommentCnt() + 1);
            questionRepository.save(question);
        }else if(commentCreateDto.getType() == 2){
            System.out.println(commentCreateDto);
        }
        HashMap<Object, Object> map = new HashMap<>();
        map.put("message","发布成功");
        return map;
    }
    @GetMapping("/comment/{id}")
    public List<CommentInfoDto> subComment(@PathVariable("id") Integer subCmtId){
        List<CommentInfoDto> commentList = commentService.List(subCmtId, 2);
        return commentList;
    }
}
