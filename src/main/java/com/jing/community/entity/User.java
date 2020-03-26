package com.jing.community.entity;


import lombok.Data;
import org.hibernate.type.BigIntegerType;

import javax.persistence.*;

@Entity
@Data
//@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarUrl;
}
