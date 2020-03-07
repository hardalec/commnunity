package com.jing.community.dto;

import com.jing.community.repository.QuestionRepository;
import lombok.Data;
import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Data
public class PageInationDto {
//    @Autowired
//    private QuestionRepository questionRepository;

    private List<QuestionDto> questionDtoList;
    private boolean showPrevious = true;
    private boolean showFirstPage = true;
    private boolean showNext = true;
    private boolean showEndPage = true;
    private Integer currentPage;
    private Integer totalpage;
    List<Integer> pages = new ArrayList<>();

    public void setPagination(Integer totalaticles, Integer currentPage, Integer size) {
        // 处理页数
//        Integer totalaticles = (int)questionRepository.count();
//        Integer totalaticles = (int)questionRepository.count();
        Integer totalPage = 1;
        if(totalaticles > 0) totalPage = (int) Math.ceil( totalaticles / size);
        if(currentPage < 1) currentPage = 1;
        if(currentPage > totalPage) currentPage = totalPage;
        this.currentPage = currentPage;
        this.totalpage = totalPage;

        // 将所有页码装入页码列表中
        pages.add(currentPage);
        for(int i = 1; i <= 3; i++){
            if(currentPage + i <= totalPage) pages.add(currentPage + i);
            if(currentPage - i > 0) pages.add(0,currentPage - i);
        }

        // 判断是否展示前一页 和 后一页
        if(currentPage == 1) showPrevious = false;
        if(currentPage == totalPage) showNext = false;
        // 判断是否显示第一页和最后一页
        if(pages.contains(1)) showFirstPage = false;
        if(pages.contains(totalPage)) showEndPage = false;

    }
}
