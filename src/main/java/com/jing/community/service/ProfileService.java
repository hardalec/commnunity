package com.jing.community.service;

import com.jing.community.dto.PageInationDto;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public PageInationDto list(User user, Integer currentPage, Integer size) {

//         获取我的所有的文章
//        Question Q = new Question();
//        Q.setCreate(user.getId());
//        Example<Question> example =Example.of(Q);
//        PageRequest pageRequest = PageRequest.of(currentPage - 1, size);
//        Optional<Question> questionList = questionRepository.findOne(example);

        List<Question> questionList = questionRepository.findAllByCreater(user.getId());
        Integer totalaticles = questionList.size();
        Integer start = currentPage*size - size;
        Integer end = currentPage * size <= totalaticles? currentPage * size : totalaticles;
        questionList = questionList.subList(start, end);

        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageInationDto pageInationDto = new PageInationDto();
        // 获取每一个文章的作者信息
        for (Question question : questionList) {
            // 找到作者
            QuestionDto questionDto = new QuestionDto();
            // 将文章信息拷入
            BeanUtils.copyProperties(question, questionDto);
            // 将作者信息拷入
            questionDto.setUser(user);
            // 组装成列表
            questionDtoList.add(questionDto);
        }
        // 将列表拷入
        pageInationDto.setQuestionDtoList(questionDtoList);
//         该页的展示信息获取完毕，接下来获取展示的页码数量
        pageInationDto.setPagination(totalaticles, currentPage, size);

        return pageInationDto;
    }
}
