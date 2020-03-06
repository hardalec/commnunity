package com.jing.community.dto;

import com.jing.community.entity.User;
import lombok.Data;


@Data
public class QuestionDto {
    private Integer id;
    private String title;
    private String description;
    private long gmtCreate;
    private long gmtModified;
    private Integer create;
    private Integer viewCnt;
    private Integer commentCnt;
    private Integer likeCnt;
    private String tag;
    private User user;
}
