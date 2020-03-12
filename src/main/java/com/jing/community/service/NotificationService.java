package com.jing.community.service;

import com.jing.community.dto.CommentCreateDto;
import com.jing.community.dto.NotificationDto;
import com.jing.community.entity.Comment;
import com.jing.community.entity.Notification;
import com.jing.community.entity.User;
import com.jing.community.repository.CommentRepository;
import com.jing.community.repository.NotificationRepository;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationService {
    @Autowired
    QuestionService questionService;
    @Autowired
    NotificationRepository notificationRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    QuestionRepository questionRepository;
    @Autowired
    CommentRepository commentRepository;

    public void setNotification(CommentCreateDto commentCreateDto, HttpServletRequest request) {
        Notification notification = new Notification();

        notification.setType(commentCreateDto.getType());
        notification.setGmtCreate(System.currentTimeMillis());
        User user = (User) request.getSession().getAttribute("user");
        notification.setSendorID(user.getAccountId());
        // 二级回复是发送给回复人，还是文章发表人？
        if(commentCreateDto.getType() == 1){
            notification.setQuesionId(commentCreateDto.getParentId());
            notification.setReciever(questionService.getById(commentCreateDto.getParentId()).getUser().getAccountId());
        }else{
            notification.setQuesionId(questionService
                    .getById(commentRepository.findById(commentCreateDto.getParentId()).get().getParentId()).getId());
            notification.setReciever(commentRepository.findById(commentCreateDto.getParentId()).get().getCommentor());
        }
        notification.setStatus(1);
        notificationRepository.save(notification);
    }

    public void updateTopBar(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Notification> notificationList = notificationRepository.findByRecieverAndStatus(user.getAccountId(), 1);
        Integer unRead = notificationList.size();
        request.getSession().setAttribute("unRead", unRead);
    }

    public List<NotificationDto> list(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        List<Notification> notificationList = notificationRepository.findByReciever(user.getAccountId());
        List<NotificationDto> notificationDtos = new ArrayList<>();
        for (Notification notification : notificationList) {
            NotificationDto notificationDto = new NotificationDto();
            BeanUtils.copyProperties(notification, notificationDto);
            notificationDto.setSendor(userRepository.findByAccountId(notification.getSendorID()));
            notificationDto.setQuestion(questionRepository.findById(notification.getQuesionId()).get());
            notificationDtos.add(notificationDto);
        }
        return notificationDtos;
    }

    public void readNote(Integer noteId) {
        Notification read = notificationRepository.findById(noteId).get();
        read.setStatus(2);
        notificationRepository.save(read);
    }
}
