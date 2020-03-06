package com.jing.community.controller.service;

import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public List<QuestionDto> list() {
        List<Question> questionList = questionRepository.findAll();
        List<QuestionDto> questionDtoList = new ArrayList<>();
        for (Question question : questionList) {
            User user = userRepository.findById(question.getCreate()).get();
            QuestionDto questionDto = new QuestionDto();
            BeanUtils.copyProperties(question, questionDto);
            questionDto.setUser(user);
            questionDtoList.add(questionDto);
        }
        return questionDtoList;
    }
}
