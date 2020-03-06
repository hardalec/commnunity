package com.jing.community.repository;

import com.jing.community.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByToken(String token);

//    Optional<UserEntity> findById(Integer create);
}
