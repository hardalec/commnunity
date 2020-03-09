package com.jing.community.dto;

import lombok.Data;

@Data
public class CommentDto {
    private Integer questionId;
    private Integer commentorId;
    private Integer likecnt;
    private String commentorName;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
    private String content;
}
