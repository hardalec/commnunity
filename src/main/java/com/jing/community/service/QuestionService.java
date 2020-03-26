package com.jing.community.service;

import com.jing.community.dto.PageInationDto;
import com.jing.community.dto.QuestionDto;
import com.jing.community.entity.Question;
import com.jing.community.entity.User;
import com.jing.community.repository.QuestionRepository;
import com.jing.community.repository.UserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class QuestionService {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    public PageInationDto list(Integer currentPage, Integer size) {

        // 获取该页下所有的文章
        PageRequest pageRequest = PageRequest.of(currentPage - 1, size, Sort.Direction.DESC, "gmtCreate");
        Page<Question> questionList = questionRepository.findAll(pageRequest);
//        List<ProomRealTimeOut> collect = resultList.stream().sorted((h1, h2) -> h1.getCode().compareTo(h2.getCode())).collect(Collectors.toList());

        List<QuestionDto> questionDtoList = new ArrayList<>();
        PageInationDto pageInationDto = new PageInationDto();
        // 获取每一个文章的作者信息
        for (Question question : questionList) {
            // 找到作者
            User user = userRepository.findById(question.getCreater()).get();
            QuestionDto questionDto = new QuestionDto();
            // 将文章信息拷入
            BeanUtils.copyProperties(question, questionDto);
            // 将作者信息拷入
            questionDto.setUser(user);
            // 组装成列表
            questionDtoList.add(questionDto);
        }
        Integer totalaticles = (int) questionRepository.count();
        // 将列表拷入
        pageInationDto.setQuestionDtoList(questionDtoList);
        // 该页的展示信息获取完毕，接下来获取展示的页码数量
        pageInationDto.setPagination(totalaticles, currentPage, size);

        return pageInationDto;
    }

    public QuestionDto getById(Integer id) {
        QuestionDto questionDto = new QuestionDto();
        Question question = questionRepository.findById(id).get();
        BeanUtils.copyProperties(question, questionDto);
        User user = userRepository.findById(question.getCreater()).get();
        questionDto.setUser(user);
        return questionDto;
    }

    public void incView(Integer id) {
        Question question = questionRepository.findById(id).get();
        question.setViewCnt(question.getViewCnt() + 1);
        questionRepository.save(question);
    }

    public String[] getQuestionTags(Integer questionId) {
        String[] tags = questionRepository.findById(questionId).get().getTag().split(",");
        return tags;
    }

    public Set<Question> getRelatedQuestion(Integer id) {
        String[] tags = getQuestionTags(id);
        Set<Question> result = new HashSet<Question>();
        for (String tag : tags) {
            List<Question> related = questionRepository.findByTagLike("%" + tag + "%");
            if (related != null || related.size() != 0) {
                for (Question question : related) {
                    if(question.getId() != id) {
                        result.add(question);
                    }
                }
            }
        }
        return result;
    }
}
