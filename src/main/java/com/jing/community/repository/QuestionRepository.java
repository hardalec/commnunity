package com.jing.community.repository;

import com.jing.community.cache.TagCache;
import com.jing.community.entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

public interface QuestionRepository extends JpaRepository<Question, Integer> {

    List<Question> findAllByCreater(Integer create);

    List<Question> findByTagLike(String s);

}
