package com.jing.community.dto;

import com.jing.community.entity.Comment;
import com.jing.community.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class CommentInfoDto {
    private Comment comment;
    private User commentor;
}
