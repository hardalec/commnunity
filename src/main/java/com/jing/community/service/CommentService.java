package com.jing.community.service;

import com.jing.community.dto.CommentCreateDto;
import com.jing.community.dto.CommentInfoDto;
import com.jing.community.entity.Comment;
import com.jing.community.entity.User;
import com.jing.community.repository.CommentRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionService questionService;

    public void setComment(CommentCreateDto commentCreateDto, HttpServletRequest request) {
        Comment comment = new Comment();
        comment.setParentId(commentCreateDto.getParentId());
        comment.setContent(commentCreateDto.getContent());
        comment.setType(commentCreateDto.getType());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        User user = (User) request.getSession().getAttribute("user");
        comment.setCommentor(user.getAccountId());
        commentRepository.save(comment);
    }

    public CommentInfoDto setCommentInfo(Comment comment) {
        CommentInfoDto commentInfoDto = new CommentInfoDto();
        commentInfoDto.setComment(comment);
        User user = userRepository.findByAccountId(comment.getCommentor());
        commentInfoDto.setCommentor(user);
        return commentInfoDto;
    }

//    public CmtWithLevelTwoDto getLevelTwoCmt(CommentInfoDto commentInfoDto){
//        List<Comment> allComments = commentRepository.findAll();
//        CmtWithLevelTwoDto cmtWithLevelTwoDto = new CmtWithLevelTwoDto();
//        List<CommentInfoDto> levelTwoCommentInfoList = new ArrayList<>();
//        for (Comment comment : allComments) {
//            if(comment.getType() == 2 && comment.getParentId() == commentInfoDto.getComment().getId()){
//                CommentInfoDto commentInfo = this.setCommentInfo(comment);
//                levelTwoCommentInfoList.add(commentInfo);
//            }
//        }
//        cmtWithLevelTwoDto.setCommentInfoDto(commentInfoDto);
//        cmtWithLevelTwoDto.setLevelTwoComments(levelTwoCommentInfoList);
//        return cmtWithLevelTwoDto;
//    }

    public List<CommentInfoDto> List(Integer Id, Integer type) {
        List<CommentInfoDto> commentInfoDtos = new ArrayList<>();
        List<Comment> commentList = commentRepository.findByParentId(Id);

        for (Comment comment : commentList) {
            if (comment.getType() == type) {
                commentInfoDtos.add(this.setCommentInfo(comment));
            }
        }
        return commentInfoDtos;
    }
}
