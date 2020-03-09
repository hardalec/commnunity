package com.jing.community.service;

import com.jing.community.dto.CommentDto;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Comment;
import com.jing.community.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    QuestionService questionService;
    public List<CommentDto> List(Integer questionId) {
        List<Comment> commentList = new ArrayList<>();
        commentList = commentRepository.findByParentId(questionId);
        QuestionDto question = questionService.getById(questionId);
        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : commentList) {
            CommentDto commentDto = new CommentDto();
            commentDto.setContent(comment.getContent());
            commentDto.setGmtCreate(comment.getGmtCreate());
            commentDto.setGmtModified(comment.getGmtModified());
            commentDto.setLikecnt(comment.getLikecnt());
            commentDto.setQuestionId(questionId);
            commentDto.setCommentorName(comment.getCommentor());
            commentDto.setAvatarUrl(question.getUser().getAvatarUrl());
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
