package com.jing.community.controller;

import com.jing.community.dto.NotificationDto;
import com.jing.community.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;

    @GetMapping("/notificate")
    public String readNotificate(HttpServletRequest request,
                                 Model model) {
        List<NotificationDto> notes = notificationService.list(request);
        model.addAttribute("notes", notes);
        // 需要有一个post, 得到noticcate 的id， 和跳转的问题id。
        return "notificate";
    }

    @GetMapping("/notificate/{noteId}/{questionId}")
    public String goToQuestion(@PathVariable("noteId") Integer noteId,
                               @PathVariable("questionId") Integer questionId,
                               HttpServletRequest request) {
        notificationService.readNote(noteId);
        notificationService.updateTopBar(request);
        return "redirect:/question/" + questionId;
    }
}
