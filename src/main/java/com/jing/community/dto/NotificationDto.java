package com.jing.community.dto;

import com.jing.community.entity.Comment;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import lombok.Data;

import java.util.Optional;

@Data
public class NotificationDto {
    private Integer id;
    private Integer status;
    private Integer type;
//    private String quesionId
//    private String sendorID;
//    private String recieveAccountId;
    private long gmtCreate;
    private User sendor;
    private Question question;

}
